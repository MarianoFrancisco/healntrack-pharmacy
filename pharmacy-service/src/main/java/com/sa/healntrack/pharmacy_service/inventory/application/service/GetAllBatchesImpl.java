package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_batches.GetAllBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_batches.GetAllBatchesQuery;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllBatchesImpl implements GetAllBatches {

    private final com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines findMedicines;
    private final FindBatches findBatches;

    @Override
    public List<Batch> handle(GetAllBatchesQuery query) {
        UUID medicineId = findMedicines.findByCode(query.medicineCode())
                .map(m -> m.getId().value())
                .orElseThrow(() -> new MedicineNotFoundException(query.medicineCode()));

        return findBatches.findByMedicine(medicineId, query.onlyWithStock(), query.onlyNotExpired());
    }
}
