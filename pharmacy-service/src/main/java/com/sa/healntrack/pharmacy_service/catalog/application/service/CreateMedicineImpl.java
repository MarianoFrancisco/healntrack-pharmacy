package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.DuplicateMedicineCodeException;
import com.sa.healntrack.pharmacy_service.catalog.application.mapper.MedicineMapper;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.create_medicine.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.*;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateMedicineImpl implements CreateMedicine {
    private final FindMedicines findMedicines;
    private final StoreMedicine storeMedicine;

    @Override
    public Medicine handle(CreateMedicineCommand c) {
        if (findMedicines.existsByCode(c.code())) {
            throw new DuplicateMedicineCodeException(c.code());
        }
        Medicine med = MedicineMapper.toDomain(c);
        return storeMedicine.save(med);
    }
}
