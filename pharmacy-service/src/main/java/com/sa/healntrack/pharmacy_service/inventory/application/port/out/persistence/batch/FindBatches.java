package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch;

import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;

import java.util.List;
import java.util.UUID;

public interface FindBatches {
    List<Batch> findAll(Boolean onlyWithStock, Boolean onlyNotExpired);

    List<Batch> findConsumableBatches(UUID medicineId);
}
