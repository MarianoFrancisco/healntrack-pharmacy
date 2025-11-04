package com.sa.healntrack.pharmacy_service.catalog.domain.value_object;

public enum UnitType {
    UNIT,
    TAB,
    CAP,
    AMP,
    VIAL,
    BOTTLE,
    BOX;

    public static UnitType safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El tipo de unidad no puede estar vacío");
        }
        try {
            return UnitType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Tipo de unidad inválido"
            );
        }
    }
}
