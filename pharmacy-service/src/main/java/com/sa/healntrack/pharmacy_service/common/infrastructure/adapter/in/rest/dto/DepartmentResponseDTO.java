package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.in.rest.dto;

public record DepartmentResponseDTO(
        String code,
        String name,
        String description,
        boolean isActive
) {
}