package com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_all_employees;

import java.util.UUID;

public record Employee(
        UUID id,
        String cui,
        String fullname,
        String email,
        Department department,
        boolean isActive
) {
}
