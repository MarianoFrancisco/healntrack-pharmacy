package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

public enum BuyerType {
    HOSPITALIZATION,
    PATIENT;

    public static BuyerType safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El tipo de comprador no puede estar vacío");
        }
        try {
            return BuyerType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Tipo de comprador inválido"
            );
        }
    }
}
