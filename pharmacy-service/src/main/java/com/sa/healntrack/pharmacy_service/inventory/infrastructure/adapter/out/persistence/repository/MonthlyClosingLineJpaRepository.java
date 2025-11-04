package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.MonthlyClosingLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MonthlyClosingLineJpaRepository extends JpaRepository<MonthlyClosingLineEntity, UUID> {
}
