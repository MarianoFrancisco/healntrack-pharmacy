package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

import java.util.UUID;

public record ClosedById(UUID value) {
    public ClosedById {
        if (value == null) throw new IllegalArgumentException("El identificador del usuario que cierra no puede estar vacío");
    }
}
