package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

import java.util.UUID;

public record BatchId(UUID value) {
    public BatchId {
        if (value == null) throw new IllegalArgumentException("El id del lote no puede estar vacío");
    }
}
