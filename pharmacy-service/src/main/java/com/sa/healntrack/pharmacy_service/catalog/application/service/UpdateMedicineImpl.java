package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.update_medicine.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.*;
import com.sa.healntrack.pharmacy_service.catalog.application.mapper.MedicineMapper;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateMedicineImpl implements UpdateMedicine {
    private final FindMedicines findMedicines;
    private final StoreMedicine storeMedicine;

    @Override
    public Medicine handle(String code, UpdateMedicineCommand c) {
        Medicine m = findMedicines.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Medicina no encontrada: " + code));
        MedicineMapper.updateDomain(m, c);
        return storeMedicine.save(m);
    }
}
