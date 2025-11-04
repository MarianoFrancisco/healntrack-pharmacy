package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence;

import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.StoreSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.domain.StockSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.StockSnapshotEntity;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper.SnapshotEntityMapper;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository.StockSnapshotJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockSnapshotRepository implements FindSnapshot, StoreSnapshot {

    private final StockSnapshotJpaRepository jpa;

    @Override
    public Optional<StockSnapshot> findByMedicineId(UUID medicineId) {
        return jpa.findById(medicineId).map(SnapshotEntityMapper::toDomain);
    }

    @Override
    public int getTotalQuantity(UUID medicineId) {
        return jpa.findById(medicineId)
                .map(s -> s.getTotalQuantity() != null ? s.getTotalQuantity() : 0)
                .orElse(0);
    }

    @Override
    public void increase(UUID medicineId, int delta) {
        StockSnapshotEntity entity = jpa.findById(medicineId)
                .orElseGet(() -> StockSnapshotEntity.builder()
                        .medicineId(medicineId)
                        .totalQuantity(0)
                        .updatedAt(java.time.OffsetDateTime.now())
                        .build());

        int next = (entity.getTotalQuantity() != null ? entity.getTotalQuantity() : 0) + Math.max(0, delta);
        entity.setTotalQuantity(next);
        entity.setUpdatedAt(java.time.OffsetDateTime.now());
        jpa.save(entity);
    }

    @Override
    public void decrease(UUID medicineId, int delta) {
        StockSnapshotEntity entity = jpa.findById(medicineId)
                .orElseThrow(() -> new IllegalStateException("No existe snapshot para la medicina: " + medicineId));

        int current = entity.getTotalQuantity() != null ? entity.getTotalQuantity() : 0;
        int next = current - Math.max(0, delta);
        if (next < 0) {
            throw new IllegalStateException("El stock quedaría negativo");
        }

        entity.setTotalQuantity(next);
        entity.setUpdatedAt(java.time.OffsetDateTime.now());
        jpa.save(entity);
    }
}
