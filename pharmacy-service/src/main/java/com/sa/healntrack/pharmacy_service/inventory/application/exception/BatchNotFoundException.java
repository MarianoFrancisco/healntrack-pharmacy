package com.sa.healntrack.pharmacy_service.inventory.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class BatchNotFoundException extends BusinessException {
    public BatchNotFoundException() {
        super("Lote no encontrado");
    }
}
