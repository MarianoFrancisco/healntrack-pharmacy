package com.sa.healntrack.pharmacy_service.catalog.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.BusinessException;

public class NoMedicineCodesProvidedException extends BusinessException {
    public NoMedicineCodesProvidedException() {
        super("Debe proporcionar al menos un código de medicina.");
    }
}
