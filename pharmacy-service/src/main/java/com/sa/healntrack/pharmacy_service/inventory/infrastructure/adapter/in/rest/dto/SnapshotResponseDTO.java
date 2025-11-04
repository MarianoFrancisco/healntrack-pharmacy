package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record SnapshotResponseDTO(
        UUID medicineId,
        int totalQuantity,
        long updatedAt
) {
}
