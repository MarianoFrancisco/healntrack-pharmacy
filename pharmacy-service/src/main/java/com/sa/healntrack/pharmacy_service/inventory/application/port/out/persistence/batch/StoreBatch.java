package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch;

import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;

public interface StoreBatch {
    Batch save(Batch batch);
}
