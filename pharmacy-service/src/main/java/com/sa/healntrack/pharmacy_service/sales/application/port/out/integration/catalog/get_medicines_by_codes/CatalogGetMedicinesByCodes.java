package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_medicines_by_codes;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;

public interface CatalogGetMedicinesByCodes {
    List<Medicine> get(CatalogGetMedicinesByCodesQuery query);
}
