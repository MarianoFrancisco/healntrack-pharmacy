package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot;

import java.util.UUID;

public interface StoreSnapshot {
    void increase(UUID medicineId, int quantity);

    void decrease(UUID medicineId, int quantity);
}
