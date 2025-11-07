package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.integration.inventory;

import com.sa.healntrack.pharmacy_service.catalog.application.port.out.integration.inventory.get_all_snapshots.InventoryGetAllSnapshots;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_all_snapshots.GetAllSnapshots;
import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllSnapshotsInProcess implements InventoryGetAllSnapshots {

    private final GetAllSnapshots getAllSnapshots;

    @Override
    public List<StockSnapshot> getAll() {
        return getAllSnapshots.handle();
    }
}
