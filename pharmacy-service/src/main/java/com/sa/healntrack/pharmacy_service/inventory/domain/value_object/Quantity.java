package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

public record Quantity(int value) {
    public Quantity {
        if (value < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");
    }

    public static Quantity ofPositive(int v) {
        if (v <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        return new Quantity(v);
    }

    public Quantity add(int delta) {
        return new Quantity(Math.addExact(value, delta));
    }

    public Quantity sub(int delta) {
        int next = value - delta;
        if (next < 0) throw new IllegalStateException("La cantidad no puede ser negativa");
        return new Quantity(next);
    }
}
