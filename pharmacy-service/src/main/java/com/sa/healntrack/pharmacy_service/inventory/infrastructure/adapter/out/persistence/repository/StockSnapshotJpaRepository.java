package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.StockSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockSnapshotJpaRepository extends JpaRepository<StockSnapshotEntity, UUID> {
}
