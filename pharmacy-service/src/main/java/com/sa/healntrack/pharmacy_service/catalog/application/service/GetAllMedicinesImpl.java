package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.GetAllMedicines;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.GetAllMedicinesQuery;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllMedicinesImpl implements GetAllMedicines {
    private final FindMedicines findMedicines;

    @Override
    public List<Medicine> handle(GetAllMedicinesQuery q) {
        return findMedicines.search(q.searchTerm(), q.isActive());
    }
}