package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

import java.util.UUID;

public record SellerId(UUID value) {
    public SellerId {
        if (value == null) {
            throw new IllegalArgumentException("El identificador del vendedor no puede estar vacío");
        }
    }
}
