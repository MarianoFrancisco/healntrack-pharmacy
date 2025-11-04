package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper;

import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.StockSnapshotEntity;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class SnapshotEntityMapper {

    public StockSnapshot toDomain(StockSnapshotEntity e) {
        long updated = e.getUpdatedAt().toInstant().toEpochMilli();
        return StockSnapshot.restore(e.getMedicineId(), e.getTotalQuantity(), updated);
    }

    public StockSnapshotEntity toEntity(StockSnapshot d) {
        OffsetDateTime updated = OffsetDateTime.ofInstant(Instant.ofEpochMilli(d.getUpdatedAt()), ZoneOffset.UTC);
        return StockSnapshotEntity.builder()
                .medicineId(d.getMedicineId().value())
                .totalQuantity(d.getTotalQuantity().value())
                .updatedAt(updated)
                .build();
    }
}
