package com.sa.healntrack.pharmacy_service.common.application.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entidad no encontrada");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
