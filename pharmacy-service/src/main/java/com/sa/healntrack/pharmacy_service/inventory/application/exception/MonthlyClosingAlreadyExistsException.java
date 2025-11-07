package com.sa.healntrack.pharmacy_service.inventory.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class MonthlyClosingAlreadyExistsException extends BusinessException {
    public MonthlyClosingAlreadyExistsException(int y, int m) {
        super("Cierre mensual ya existe para " + y + "-" + String.format("%02d", m));
    }
}
