package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.inventory.application.exception.InsufficientStockException;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStock;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStockCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.StoreBatch;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.StoreSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DecreaseStockImpl implements DecreaseStock {

    private final FindMedicines findMedicines;
    private final FindSnapshot findSnapshot;
    private final StoreSnapshot storeSnapshot;
    private final FindBatches findBatches;
    private final StoreBatch storeBatch;

    @Override
    public void handle(DecreaseStockCommand command) {
        UUID medicineId = findMedicines.findByCode(command.medicineCode())
                .map(m -> m.getId().value())
                .orElseThrow(() -> new MedicineNotFoundException(command.medicineCode()));

        int available = findSnapshot.getTotalQuantity(medicineId);
        if (available < command.quantity()) {
            throw new InsufficientStockException("Stock insuficiente para medicina " + command.medicineCode());
        }

        List<Batch> consumables = findBatches.findConsumableBatches(medicineId);
        int remaining = command.quantity();

        for (Batch b : consumables) {
            if (remaining == 0) {
                break;
            }
            int taken = b.consume(remaining);
            storeBatch.save(b);
            remaining = remaining - taken;
        }

        if (remaining > 0) {
            throw new InsufficientStockException("No fue posible consumir toda la cantidad solicitada");
        }

        storeSnapshot.decrease(medicineId, command.quantity());
    }
}
