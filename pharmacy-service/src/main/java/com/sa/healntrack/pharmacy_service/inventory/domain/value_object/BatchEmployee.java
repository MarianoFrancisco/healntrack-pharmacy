package com.sa.healntrack.pharmacy_service.inventory.domain.value_object;

public record BatchEmployee(
        String cui,
        String fullname,
        boolean isActive
) {
}
