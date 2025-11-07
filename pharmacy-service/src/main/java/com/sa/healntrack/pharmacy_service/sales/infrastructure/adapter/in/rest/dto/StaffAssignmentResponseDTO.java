package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record StaffAssignmentResponseDTO(
        UUID id,
        UUID employeeId,
        LocalDate assignedAt
) {
}
