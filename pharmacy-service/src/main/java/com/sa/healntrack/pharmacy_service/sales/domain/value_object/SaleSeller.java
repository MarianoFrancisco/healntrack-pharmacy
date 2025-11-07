package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

public record SaleSeller(
        String cui,
        String fullname,
        boolean isActive
) {
}
