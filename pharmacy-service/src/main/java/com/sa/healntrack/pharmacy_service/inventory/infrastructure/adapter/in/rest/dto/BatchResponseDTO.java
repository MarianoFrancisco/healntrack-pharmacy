package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.BatchEmployee;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.BatchMedicine;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        long updatedAt,
        BatchMedicine medicine,
        BatchEmployee employee
) {
}
