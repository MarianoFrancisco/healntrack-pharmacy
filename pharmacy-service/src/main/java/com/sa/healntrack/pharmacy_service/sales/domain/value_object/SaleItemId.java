package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

import java.util.UUID;

public record SaleItemId(UUID value) {
    public SaleItemId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador del ítem de venta no puede estar vacío");
        }
    }
}
