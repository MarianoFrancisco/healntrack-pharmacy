package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateMonthlyClosingDTO(

        @NotNull(message = "El identificador del usuario que realiza el cierre es obligatorio")
        UUID closedBy,

        @NotNull(message = "La lista de líneas del cierre no puede ser nula")
        @Valid List<CreateMonthlyClosingLineDTO> lines
) {
}