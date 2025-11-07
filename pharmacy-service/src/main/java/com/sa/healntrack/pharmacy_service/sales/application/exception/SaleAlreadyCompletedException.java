package com.sa.healntrack.pharmacy_service.sales.application.exception;

public class SaleAlreadyCompletedException extends RuntimeException {
    public SaleAlreadyCompletedException() {
        super("La venta ya está completada");
    }
}
