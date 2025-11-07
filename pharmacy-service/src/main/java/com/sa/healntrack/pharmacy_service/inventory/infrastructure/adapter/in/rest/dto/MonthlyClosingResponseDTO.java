package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record MonthlyClosingResponseDTO(
        UUID id,
        int year,
        int month,
        UUID closedBy,
        long closedAt
) {
}
