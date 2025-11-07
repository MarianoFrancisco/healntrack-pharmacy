package com.sa.healntrack.pharmacy_service.common.infrastructure.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class SerializerException extends BusinessException {
    public SerializerException(String error) {
        super("Error al serializar la clase: " + error);
    }
}
