package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface BatchJpaRepository extends JpaRepository<BatchEntity, UUID>, JpaSpecificationExecutor<BatchEntity> {
    List<BatchEntity> findByMedicineIdOrderByCreatedAtAsc(UUID medicineId);
}
