package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.deactivate_medicine.DeactivateMedicine;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.deactivate_medicine.DeactivateMedicineCommand;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.StoreMedicine;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeactivateMedicineImpl implements DeactivateMedicine {
    private final FindMedicines findMedicines;
    private final StoreMedicine storeMedicine;

    @Override
    public void handle(DeactivateMedicineCommand c) {
        Medicine m = findMedicines.findByCode(c.code())
                .orElseThrow(() -> new MedicineNotFoundException(c.code()));
        m.deactivate();
        storeMedicine.save(m);
    }
}