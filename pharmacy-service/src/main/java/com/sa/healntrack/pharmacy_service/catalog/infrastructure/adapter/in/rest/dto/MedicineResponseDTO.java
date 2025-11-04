package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.dto;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineStatus;
import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.UnitType;

import java.math.BigDecimal;
import java.util.UUID;

public record MedicineResponseDTO(
        UUID id,
        String code,
        String name,
        String description,
        MedicineStatus status,
        UnitType unitType,
        Integer minStock,
        BigDecimal currentPrice,
        BigDecimal currentCost,
        long createdAt,
        long updatedAt
) {}
