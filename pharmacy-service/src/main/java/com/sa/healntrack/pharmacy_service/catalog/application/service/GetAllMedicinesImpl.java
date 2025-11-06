package com.sa.healntrack.pharmacy_service.catalog.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.GetAllMedicines;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.GetAllMedicinesQuery;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.integration.inventory.get_all_snapshots.InventoryGetAllSnapshots;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllMedicinesImpl implements GetAllMedicines {
    private final FindMedicines findMedicines;
    private final InventoryGetAllSnapshots inventoryGetAllSnapshots;

    public List<Medicine> handle(GetAllMedicinesQuery q) {
        List<Medicine> medicines = findMedicines.search(q.searchTerm(), q.isActive());
        List<StockSnapshot> snapshots = inventoryGetAllSnapshots.getAll();

        Map<UUID, Integer> stockMap = snapshots.stream()
                .collect(Collectors.toMap(
                        s -> s.getMedicineId().value(),
                        s -> s.getTotalQuantity().value()
                ));

        medicines.forEach(m -> m.setStock(stockMap.getOrDefault(m.getId().value(), 0)));

        return medicines;
    }
}