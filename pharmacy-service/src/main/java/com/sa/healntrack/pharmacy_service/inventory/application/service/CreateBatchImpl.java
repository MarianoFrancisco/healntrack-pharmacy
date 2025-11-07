package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSold;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSoldCommand;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.ReportTransactionType;
import com.sa.healntrack.pharmacy_service.inventory.application.mapper.BatchMapper;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_batch.CreateBatch;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_batch.CreateBatchCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.StoreBatch;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.StoreSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateBatchImpl implements CreateBatch {

    private final FindMedicines findMedicines;
    private final StoreBatch storeBatch;
    private final StoreSnapshot storeSnapshot;
    private final PublishMedicineSold publishMedicineSold;

    @Override
    public void handle(CreateBatchCommand command) {
        Medicine med = findMedicines.findByCode(command.medicineCode())
                .orElseThrow(() -> new MedicineNotFoundException(command.medicineCode()));

        Batch batch = BatchMapper.toDomain(med, command);
        storeBatch.save(batch);
        storeSnapshot.increase(med.getId().value(), command.purchasedQuantity());

        BigDecimal lineTotal = batch.getPurchasePrice().value()
                .multiply(BigDecimal.valueOf(command.purchasedQuantity()));
        publishMedicineSold.publish(
                new PublishMedicineSoldCommand(
                        med.getId().value(),
                        "PHARMACY",
                        LocalDate.now(),
                        ReportTransactionType.EXPENSE,
                        lineTotal
                )
        );
    }
}
