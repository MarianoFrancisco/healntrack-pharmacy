package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateSaleDTO(
        @NotNull UUID sellerId,
        @NotNull List<CreateSaleItemDTO> items
) {
}
