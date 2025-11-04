package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateMonthlyClosingDTO(
        UUID closedBy,
        @NotNull @Valid List<CreateMonthlyClosingLineDTO> lines
) {
}
