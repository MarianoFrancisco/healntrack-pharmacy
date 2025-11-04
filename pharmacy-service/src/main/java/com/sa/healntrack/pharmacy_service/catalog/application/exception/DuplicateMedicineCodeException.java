package com.sa.healntrack.pharmacy_service.catalog.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.DuplicateEntityException;

public class DuplicateMedicineCodeException extends DuplicateEntityException {
    public DuplicateMedicineCodeException(String code) {
        super("El código de medicina ya existe: " + code);
    }
}
