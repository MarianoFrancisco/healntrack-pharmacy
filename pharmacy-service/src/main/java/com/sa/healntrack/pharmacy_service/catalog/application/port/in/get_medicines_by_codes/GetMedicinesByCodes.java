package com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicines_by_codes;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;

public interface GetMedicinesByCodes {
    List<Medicine> handle(GetMedicinesByCodesQuery query);
}
