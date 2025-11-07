package com.sa.healntrack.pharmacy_service.sales.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class SaleAlreadyCompletedException extends BusinessException {
    public SaleAlreadyCompletedException() {
        super("La venta ya está completada");
    }
}
