package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot;

import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;

import java.util.Optional;
import java.util.UUID;

public interface FindSnapshot {
    Optional<StockSnapshot> findByMedicineId(UUID medicineId);

    int getTotalQuantity(UUID medicineId);
}
