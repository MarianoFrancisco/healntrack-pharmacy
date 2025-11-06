package com.sa.healntrack.pharmacy_service.catalog.application.port.out.integration.inventory.get_all_snapshots;

import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;

import java.util.List;

public interface InventoryGetAllSnapshots {
    List<StockSnapshot> getAll();
}
