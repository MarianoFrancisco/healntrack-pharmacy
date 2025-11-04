package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.MonthlyClosingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MonthlyClosingJpaRepository extends JpaRepository<MonthlyClosingEntity, UUID> {
    boolean existsByYearAndMonth(int year, int month);
}
