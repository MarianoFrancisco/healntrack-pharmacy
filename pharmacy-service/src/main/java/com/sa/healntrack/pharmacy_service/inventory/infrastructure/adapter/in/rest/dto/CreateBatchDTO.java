package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBatchDTO(
        @NotBlank String medicineCode,
        LocalDate expirationDate,
        @Min(1) int purchasedQuantity,
        UUID purchasedBy
) {
}
