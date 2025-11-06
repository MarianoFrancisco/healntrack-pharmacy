package com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_all_snapshots;

import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;

import java.util.List;

public interface GetAllSnapshots {
    List<StockSnapshot> handle();
}
