package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

public enum SaleStatus {
    OPEN,
    COMPLETED;

    public static SaleStatus safeValueOf(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El estado de la venta no puede estar vacío");
        }
        try {
            return SaleStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Estado de la venta inválido"
            );
        }
    }
}
