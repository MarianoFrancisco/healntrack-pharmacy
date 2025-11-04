package com.sa.healntrack.pharmacy_service.catalog.application.port.in.create_medicine;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

public interface CreateMedicine {
    Medicine handle(CreateMedicineCommand command);
}
