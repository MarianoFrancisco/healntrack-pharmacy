package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateSaleItemDTO(
        @NotBlank String medicineCode,
        @Min(1) int quantity
) {
}
