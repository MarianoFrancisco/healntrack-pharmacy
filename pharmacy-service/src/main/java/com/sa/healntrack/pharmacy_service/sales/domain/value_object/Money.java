package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

import java.math.BigDecimal;
import java.util.List;

public record Money(BigDecimal value) {

    public Money {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto monetario no puede ser negativo");
        }
    }

    public Money add(Money other) {
        return new Money(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        BigDecimal r = this.value.subtract(other.value);
        if (r.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Resultado monetario negativo");
        }
        return new Money(r);
    }

    public Money multiply(int qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
        return new Money(this.value.multiply(BigDecimal.valueOf(qty)));
    }

    public static Money sum(List<BigDecimal> list) {
        BigDecimal acc = BigDecimal.ZERO;
        for (BigDecimal v : list) {
            if (v != null) {
                acc = acc.add(v);
            }
        }
        return new Money(acc);
    }
}
