package com.sa.healntrack.pharmacy_service.inventory.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(String code) {
        super("Stock insuficiente para medicina: " + code);
    }
}
