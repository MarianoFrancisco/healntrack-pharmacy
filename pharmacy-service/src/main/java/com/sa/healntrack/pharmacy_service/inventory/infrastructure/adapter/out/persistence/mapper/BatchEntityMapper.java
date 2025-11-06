package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper;

import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.BatchEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@UtilityClass
public class BatchEntityMapper {

    public Batch toDomain(BatchEntity e) {
        long created = e.getCreatedAt().toInstant().toEpochMilli();
        long updated = e.getUpdatedAt().toInstant().toEpochMilli();
        BigDecimal price = e.getPurchasePrice();

        return Batch.restore(
                e.getId(),
                e.getMedicineId(),
                e.getExpirationDate(),
                e.getPurchasedQuantity(),
                e.getQuantityOnHand(),
                price,
                e.getPurchasedBy(),
                created,
                updated
        );
    }

    public BatchEntity toEntity(Batch d) {
        OffsetDateTime created = OffsetDateTime.ofInstant(Instant.ofEpochMilli(d.getCreatedAt()), ZoneOffset.UTC);
        OffsetDateTime updated = OffsetDateTime.ofInstant(Instant.ofEpochMilli(d.getUpdatedAt()), ZoneOffset.UTC);

        return BatchEntity.builder()
                .id(d.getId() != null ? d.getId().value() : UUID.randomUUID())
                .medicineId(d.getMedicineId().value())
                .expirationDate(d.getExpirationDate())
                .purchasedQuantity(d.getPurchasedQuantity().value())
                .quantityOnHand(d.getQuantityOnHand().value())
                .purchasePrice(d.getPurchasePrice() != null ? d.getPurchasePrice().value() : null)
                .purchasedBy(d.getPurchasedBy().value())
                .createdAt(created)
                .updatedAt(updated)
                .build();
    }
}
