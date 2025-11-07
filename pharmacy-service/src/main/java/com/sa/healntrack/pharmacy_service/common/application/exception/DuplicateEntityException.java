package com.sa.healntrack.pharmacy_service.common.application.exception;

public class DuplicateEntityException extends BusinessException {
    public DuplicateEntityException() {
        super("Entidad ya existe");
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
