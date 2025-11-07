package com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee;

public record Department(
        String name,
        String description,
        boolean isActive
) {
}
