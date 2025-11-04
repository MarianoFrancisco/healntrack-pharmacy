package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateMonthlyClosingLineDTO(
        @NotBlank String medicineCode,
        @Min(0) Integer physicalQty,
        String note
) {
}
