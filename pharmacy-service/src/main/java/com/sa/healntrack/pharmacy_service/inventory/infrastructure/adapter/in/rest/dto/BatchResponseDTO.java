package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BatchResponseDTO(
        UUID id,
        UUID medicineId,
        LocalDate expirationDate,
        int purchasedQuantity,
        int quantityOnHand,
        BigDecimal purchasePrice,
        UUID purchasedBy,
        long createdAt,
        long updatedAt
) {
}
