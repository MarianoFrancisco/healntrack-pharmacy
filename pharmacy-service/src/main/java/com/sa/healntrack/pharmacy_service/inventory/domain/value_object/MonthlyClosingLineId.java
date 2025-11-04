package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

import java.util.UUID;

public record MonthlyClosingLineId(UUID value) {
    public MonthlyClosingLineId {
        if (value == null)
            throw new IllegalArgumentException("El identificador de la línea de cierre mensual no puede estar vacío");
    }
}
