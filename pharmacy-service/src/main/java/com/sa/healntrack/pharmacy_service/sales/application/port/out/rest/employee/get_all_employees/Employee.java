package com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees;

public record Employee(
        String cui,
        String fullname,
        String email,
        Department department,
        boolean isActive
) {
}
