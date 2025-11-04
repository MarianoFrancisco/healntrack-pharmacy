package com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_snapshot;

import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;

public interface GetSnapshot {
    StockSnapshot handle(GetSnapshotQuery q);
}
