package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleItemMedicine;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemResponseDTO(
        UUID id,
        UUID medicineId,
        String medicineCode,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal unitCost,
        BigDecimal lineTotal,
        SaleItemMedicine medicine
) {
}
