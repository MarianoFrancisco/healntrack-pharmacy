package com.sa.healntrack.pharmacy_service.catalog.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.EntityNotFoundException;

public class MedicineNotFoundException extends EntityNotFoundException {
    public MedicineNotFoundException(String code) {
        super("Medicina con código " + code + " no encontrada");
    }
}
