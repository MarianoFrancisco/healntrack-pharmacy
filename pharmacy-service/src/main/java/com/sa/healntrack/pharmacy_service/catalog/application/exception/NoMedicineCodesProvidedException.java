package com.sa.healntrack.pharmacy_service.catalog.application.exception;

public class NoMedicineCodesProvidedException extends RuntimeException {
    public NoMedicineCodesProvidedException() {
        super("Debe proporcionar al menos un código de medicina.");
    }
}
