package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.entity.SaleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleJpaRepository extends JpaRepository<SaleEntity, UUID>, JpaSpecificationExecutor<SaleEntity> {

    @Override
    @EntityGraph(attributePaths = "items")
    Optional<SaleEntity> findById(UUID id);

    @EntityGraph(attributePaths = "items")
    List<SaleEntity> findAll(Specification<SaleEntity> spec);
}
