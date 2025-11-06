package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSaleItemDTO(
        @NotBlank(message = "El código del medicamento es obligatorio")
        String medicineCode,

        @Min(value = 1, message = "La cantidad mínima es 1")
        int quantity
) {
}
