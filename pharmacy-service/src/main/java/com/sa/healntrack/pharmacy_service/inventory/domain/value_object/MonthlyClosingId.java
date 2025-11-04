package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

import java.util.UUID;

public record MonthlyClosingId(UUID value) {
    public MonthlyClosingId {
        if (value == null) throw new IllegalArgumentException("El identificador del cierre mensual no puede ser nulo");
    }
}
