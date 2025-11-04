package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_snapshot.GetSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_snapshot.GetSnapshotQuery;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetSnapshotImpl implements GetSnapshot {

    private final FindMedicines findMedicines;
    private final FindSnapshot findSnapshot;

    @Override
    public StockSnapshot handle(GetSnapshotQuery query) {
        UUID medId = findMedicines.findByCode(query.medicineCode())
                .map(m -> m.getId().value())
                .orElseThrow(() -> new MedicineNotFoundException(query.medicineCode()));

        return findSnapshot.findByMedicineId(medId)
                .orElse(new StockSnapshot(medId, 0));
    }
}
