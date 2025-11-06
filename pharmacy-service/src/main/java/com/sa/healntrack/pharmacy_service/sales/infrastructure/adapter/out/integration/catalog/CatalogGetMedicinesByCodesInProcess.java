package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.integration.catalog;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicines_by_codes.GetMedicinesByCodes;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicines_by_codes.GetMedicinesByCodesQuery;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_medicines_by_codes.CatalogGetMedicinesByCodes;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_medicines_by_codes.CatalogGetMedicinesByCodesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatalogGetMedicinesByCodesInProcess implements CatalogGetMedicinesByCodes {

    private final GetMedicinesByCodes getMedicinesByCodes;

    @Override
    public List<Medicine> get(CatalogGetMedicinesByCodesQuery query) {
        return getMedicinesByCodes.handle(new GetMedicinesByCodesQuery(query.codes()));
    }
}
