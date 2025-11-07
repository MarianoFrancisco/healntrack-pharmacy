package com.sa.healntrack.pharmacy_service.common.infrastructure.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class DeserializerException extends BusinessException {
    public DeserializerException(String error) {
        super("Error al deserializar la clase: " + error);
    }
}
