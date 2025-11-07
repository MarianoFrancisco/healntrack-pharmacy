package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicine_by_code.GetMedicineByCode;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicine_by_code.GetMedicineByCodeQuery;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMedicineByCodeImpl implements GetMedicineByCode {
    private final FindMedicines findMedicines;

    @Override
    public Medicine handle(GetMedicineByCodeQuery q) {
        return findMedicines.findByCode(q.code())
                .orElseThrow(() -> new MedicineNotFoundException(q.code()));
    }
}