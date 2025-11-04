package com.sa.healntrack.pharmacy_service.sales.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.EntityNotFoundException;

public class SaleNotFoundException extends EntityNotFoundException {
    public SaleNotFoundException(String id) {
        super("Venta no encontrada: " + id);
    }
}
