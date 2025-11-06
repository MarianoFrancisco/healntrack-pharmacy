package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicinesNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.exception.NoMedicineCodesProvidedException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicines_by_codes.GetMedicinesByCodes;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicines_by_codes.GetMedicinesByCodesQuery;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetMedicinesByCodesImpl implements GetMedicinesByCodes {

    private final FindMedicines findMedicines;

    @Override
    public List<Medicine> handle(GetMedicinesByCodesQuery query) {
        if (query == null || query.codes() == null || query.codes().isEmpty()) {
            throw new NoMedicineCodesProvidedException();
        }

        Set<String> normalizedCodes = query.codes().stream()
                .filter(code -> code != null && !code.isBlank())
                .map(code -> code.trim().toUpperCase(Locale.ROOT))
                .collect(Collectors.toSet());

        List<Medicine> medicines = findMedicines.findByCodes(normalizedCodes);

        Set<String> foundCodes = medicines.stream()
                .map(medicine -> medicine.getCode().value())
                .collect(Collectors.toSet());

        List<String> missing = normalizedCodes.stream()
                .filter(code -> !foundCodes.contains(code))
                .toList();

        if (!missing.isEmpty()) {
            throw new MedicinesNotFoundException(missing);
        }

        return medicines;
    }
}
