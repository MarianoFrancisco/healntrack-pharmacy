package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot;

import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;

import java.util.*;

public interface FindSnapshot {
    Optional<StockSnapshot> findByMedicineId(UUID medicineId);

    List<StockSnapshot> findAll();

    int getTotalQuantity(UUID medicineId);
}
