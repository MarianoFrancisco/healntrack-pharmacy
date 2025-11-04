package com.sa.healntrack.pharmacy_service.catalog.application.port.in.update_medicine;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

public interface UpdateMedicine {
    Medicine handle(String code, UpdateMedicineCommand command);
}
