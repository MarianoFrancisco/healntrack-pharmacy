package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleBuyer;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleSeller;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record SaleResponseDTO(
        UUID id,
        long occurredAt,
        UUID sellerId,
        UUID buyerId,
        String buyperType,
        SaleStatus status,
        BigDecimal total,
        List<SaleItemResponseDTO> items,
        SaleSeller seller,
        SaleBuyer buyer
) {
}
