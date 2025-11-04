package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

import java.math.BigDecimal;

public record PositiveMoney(BigDecimal value) {
    public PositiveMoney {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El monto debe ser positivo o cero");
    }

    public PositiveMoney(String s) {
        this(new BigDecimal(s));
    }
}
