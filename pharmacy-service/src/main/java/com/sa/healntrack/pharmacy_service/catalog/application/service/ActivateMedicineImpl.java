package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.activate_medicine.ActivateMedicine;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.activate_medicine.ActivateMedicineCommand;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.StoreMedicine;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ActivateMedicineImpl implements ActivateMedicine {
    private final FindMedicines findMedicines;
    private final StoreMedicine storeMedicine;

    @Override
    public void handle(ActivateMedicineCommand c) {
        Medicine m = findMedicines.findByCode(c.code())
                .orElseThrow(() -> new MedicineNotFoundException(c.code()));
        m.activate();
        storeMedicine.save(m);
    }
}