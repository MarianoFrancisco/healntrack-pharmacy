package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record CreateBatchDTO(
        @NotBlank(message = "El código de la medicina es obligatorio")
        String medicineCode,

        @NotNull(message = "La fecha de expiración es obligatoria")
        @Future(message = "La fecha de expiración debe ser una fecha futura")
        LocalDate expirationDate,

        @NotNull(message = "La cantidad comprada es obligatoria")
        @Min(value = 1, message = "La cantidad comprada debe ser al menos 1")
        Integer purchasedQuantity,

        @NotNull(message = "El identificador del comprador es obligatorio")
        UUID purchasedBy
) {
}
