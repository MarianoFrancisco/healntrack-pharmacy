package com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicine_by_id;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

public interface GetMedicineByCode {
    Medicine handle(GetMedicineByCodeQuery query);
}
