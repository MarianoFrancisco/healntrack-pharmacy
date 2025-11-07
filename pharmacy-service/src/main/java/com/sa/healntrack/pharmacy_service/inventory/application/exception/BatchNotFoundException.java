package com.sa.healntrack.pharmacy_service.inventory.application.exception;

public class BatchNotFoundException extends RuntimeException {
    public BatchNotFoundException() {
        super("Lote no encontrado");
    }
}
