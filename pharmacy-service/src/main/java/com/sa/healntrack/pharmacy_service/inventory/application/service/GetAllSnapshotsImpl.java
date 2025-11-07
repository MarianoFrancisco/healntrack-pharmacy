package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_all_snapshots.GetAllSnapshots;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllSnapshotsImpl implements GetAllSnapshots {

    private final FindSnapshot findSnapshot;

    @Override
    public List<StockSnapshot> handle() {
        return findSnapshot.findAll();
    }
}
