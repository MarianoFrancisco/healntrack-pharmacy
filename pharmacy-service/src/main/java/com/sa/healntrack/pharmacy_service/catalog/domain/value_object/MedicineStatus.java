package com.sa.healntrack.pharmacy_service.catalog.domain.value_object;

public enum MedicineStatus {
    ACTIVE,
    INACTIVE;

    public static MedicineStatus safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        try {
            return MedicineStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("El estado es inválido");
        }
    }

}