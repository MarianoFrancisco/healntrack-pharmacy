package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.inventory.application.exception.MonthlyClosingAlreadyExistsException;
import com.sa.healntrack.pharmacy_service.inventory.application.mapper.MonthlyClosingLineMapper;
import com.sa.healntrack.pharmacy_service.inventory.application.mapper.MonthlyClosingMapper;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosingCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosingLineCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.closing.FindMonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.closing.StoreMonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.StoreSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosingLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateMonthlyClosingImpl implements CreateMonthlyClosing {

    private final FindMonthlyClosing findMonthlyClosing;
    private final StoreMonthlyClosing storeMonthlyClosing;
    private final FindMedicines findMedicines;
    private final FindSnapshot findSnapshot;
    private final StoreSnapshot storeSnapshot;
    private final FindBatches findBatches;

    @Override
    public void handle(CreateMonthlyClosingCommand command) {
        boolean exists = findMonthlyClosing.existsByYearMonth(command.year(), command.month());
        if (exists) {
            throw new MonthlyClosingAlreadyExistsException(command.year(), command.month());
        }

        MonthlyClosing closing = MonthlyClosingMapper.toDomain(command);

        UUID closingId = UUID.randomUUID();
        closing = MonthlyClosing.restore(
                closingId,
                command.year(),
                command.month(),
                command.closedBy(),
                closing.getClosedAt()
        );

        List<MonthlyClosingLine> lines = buildLines(closingId, command.lines());

        storeMonthlyClosing.closeWithLines(closing, lines);

        applyAdjustments(lines);
    }

    private List<MonthlyClosingLine> buildLines(UUID closingId, List<CreateMonthlyClosingLineCommand> inputLines) {
        if (inputLines == null || inputLines.isEmpty()) {
            return Collections.emptyList();
        }

        List<MonthlyClosingLine> result = new ArrayList<>();

        for (CreateMonthlyClosingLineCommand in : inputLines) {
            String code = in.medicineCode();

            Medicine med = findMedicines.findByCode(code)
                    .orElseThrow(() -> new MedicineNotFoundException(code));
            UUID medicineId = med.getId().value();

            int systemQty = findSnapshot.getTotalQuantity(medicineId);

            BigDecimal unitCost = computeWeightedAverageCost(med);
            MonthlyClosingLine line = MonthlyClosingLineMapper.of(
                    closingId,
                    medicineId,
                    systemQty,
                    in.physicalQty(),
                    unitCost,
                    in.note()
            );
            result.add(line);
        }
        return result;
    }

    private BigDecimal computeWeightedAverageCost(Medicine med) {
        List<Batch> batches = findBatches.findConsumableBatches(med.getId().value());
        BigDecimal sumAmount = BigDecimal.ZERO;
        int sumQty = 0;

        for (Batch b : batches) {
            int onHand = b.getQuantityOnHand().value();
            sumAmount = sumAmount.add(b.getPurchasePrice().value().multiply(new BigDecimal(onHand)));
            sumQty = sumQty + onHand;
        }

        if (sumQty > 0) {
            return sumAmount.divide(new BigDecimal(sumQty), 2, RoundingMode.HALF_UP);
        }

        return med.getCurrentCost();
    }

    private void applyAdjustments(List<MonthlyClosingLine> lines) {
        for (MonthlyClosingLine l : lines) {
            int variance = l.getVariance();
            if (variance == 0) {
                continue;
            }
            UUID medId = l.getMedicineId().value();
            if (variance > 0) {
                storeSnapshot.increase(medId, variance);
            } else {
                storeSnapshot.decrease(medId, Math.abs(variance));
            }
        }
    }
}
