package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper;

import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.MonthlyClosingEntity;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@UtilityClass
public class MonthlyClosingEntityMapper {

    public MonthlyClosing toDomain(MonthlyClosingEntity e) {
        long closedAt = e.getClosedAt().toInstant().toEpochMilli();
        return MonthlyClosing.restore(
                e.getId(),
                e.getYear(),
                e.getMonth(),
                e.getClosedBy(),
                closedAt
        );
    }

    public MonthlyClosingEntity toEntity(MonthlyClosing d) {
        OffsetDateTime closedAt = OffsetDateTime.ofInstant(Instant.ofEpochMilli(d.getClosedAt()), ZoneOffset.UTC);
        return MonthlyClosingEntity.builder()
                .id(d.getId() != null ? d.getId().value() : UUID.randomUUID())
                .year(d.getYear())
                .month(d.getMonth())
                .closedBy(d.getClosedBy() != null ? d.getClosedBy().value() : null)
                .closedAt(closedAt)
                .build();
    }
}
