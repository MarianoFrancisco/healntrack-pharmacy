package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

import java.util.UUID;

public record BuyerId(UUID value) {
    public BuyerId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador del comprador no puede estar vacío");
        }
    }
}
