package com.sa.healntrack.pharmacy_service.inventory.application.exception;

public class MonthlyClosingAlreadyExistsException extends RuntimeException {
    public MonthlyClosingAlreadyExistsException(int y, int m) {
        super("Cierre mensual ya existe para " + y + "-" + String.format("%02d", m));
    }
}
