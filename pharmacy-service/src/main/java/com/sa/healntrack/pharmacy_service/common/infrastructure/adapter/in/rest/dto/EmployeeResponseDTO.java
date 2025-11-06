package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID id,
        String cui,
        String nit,
        String fullname,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        DepartmentResponseDTO department,
        BigDecimal salary,
        BigDecimal igssPercent,
        BigDecimal irtraPercent,
        boolean isActive
) {
}