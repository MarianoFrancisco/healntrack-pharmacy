package com.sa.healntrack.pharmacy_service.inventory.application.mapper;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_batch.CreateBatchCommand;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BatchMapper {

    public Batch toDomain(Medicine med, CreateBatchCommand c) {
        return new Batch(
                med.getId().value(),
                c.expirationDate(),
                c.purchasedQuantity(),
                med.getCurrentCost(),
                c.purchasedBy()
        );
    }
}