package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

import java.util.UUID;

public record SaleId(UUID value) {
    public SaleId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador de la venta no puede estar vacío");
        }
    }
}
