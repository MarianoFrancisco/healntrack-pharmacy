package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.integration.catalog;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.GetAllMedicines;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.GetAllMedicinesQuery;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.integration.get_all_medicines.CatalogGetAllMedicines;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.integration.get_all_medicines.CatalogGetAllMedicinesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatalogGetAllMedicinesInProcess implements CatalogGetAllMedicines {

    private final GetAllMedicines getAllMedicines;

    @Override
    public List<Medicine> getAll(CatalogGetAllMedicinesQuery query) {
        return getAllMedicines.handle(new GetAllMedicinesQuery(null, null));
    }
}
