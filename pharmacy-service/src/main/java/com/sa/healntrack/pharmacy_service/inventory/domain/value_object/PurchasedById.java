package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

import java.util.UUID;

public record PurchasedById(UUID value) {
    public PurchasedById {
        if (value == null)
            throw new IllegalArgumentException("El identificador del usuario que realiza la compra no puede estar vacío");
    }
}
