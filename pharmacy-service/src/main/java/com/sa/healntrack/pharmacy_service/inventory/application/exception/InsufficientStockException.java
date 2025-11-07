package com.sa.healntrack.pharmacy_service.inventory.application.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String code) {
        super("Stock insuficiente para medicina: " + code);
    }
}
