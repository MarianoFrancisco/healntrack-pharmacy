package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.mapper;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.entity.MedicineEntity;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public final class MedicineEntityMapper {

    public Medicine toDomain(MedicineEntity e) {
        long created = e.getCreatedAt().toInstant().toEpochMilli();
        long updated = e.getUpdatedAt().toInstant().toEpochMilli();
        return Medicine.restore(
                e.getId(),
                e.getCode(),
                e.getName(),
                e.getDescription(),
                e.getStatus().name(),
                e.getUnitType().name(),
                e.getMinStock(),
                e.getCurrentPrice(),
                e.getCurrentCost(),
                created,
                updated
        );
    }

    public MedicineEntity toEntity(Medicine m) {
        return MedicineEntity.builder()
                .id(m.getId() != null ? m.getId().value() : null)
                .code(m.getCode().value())
                .name(m.getName())
                .description(m.getDescription())
                .status(m.getStatus())
                .unitType(m.getUnitType())
                .minStock(m.getMinStock())
                .currentPrice(m.getCurrentPrice())
                .currentCost(m.getCurrentCost())
                .createdAt(OffsetDateTime.ofInstant(Instant.ofEpochMilli(m.getCreatedAt()), ZoneOffset.UTC))
                .updatedAt(OffsetDateTime.ofInstant(Instant.ofEpochMilli(m.getUpdatedAt()), ZoneOffset.UTC))
                .build();
    }
}
