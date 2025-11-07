package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateMonthlyClosingLineDTO(

        @NotBlank(message = "El código de la medicina es obligatorio")
        String medicineCode,

        @Min(value = 0, message = "La cantidad física no puede ser negativa")
        Integer physicalQty,

        String note
) {
}