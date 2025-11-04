package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateMedicineDTO(
        @NotBlank
        @Size(max = 120)
        String name,
        @Size(max = 500)
        String description,
        @NotBlank
        String unitType,
        @NotNull
        @Min(0)
        Integer minStock,
        @NotNull
        @DecimalMin("0.0")
        BigDecimal currentPrice,
        @NotNull
        @DecimalMin("0.0")
        BigDecimal currentCost
) {
}
