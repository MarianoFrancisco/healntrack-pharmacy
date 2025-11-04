package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.entity.MedicineEntity;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface MedicineJpaRepository extends JpaRepository<MedicineEntity, java.util.UUID>, JpaSpecificationExecutor<MedicineEntity> {
    Optional<MedicineEntity> findByCode(String code);

    boolean existsByCode(String code);
}
