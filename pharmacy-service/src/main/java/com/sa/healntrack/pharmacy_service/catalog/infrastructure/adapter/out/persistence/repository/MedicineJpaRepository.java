package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.repository;

import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.entity.MedicineEntity;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface MedicineJpaRepository extends JpaRepository<MedicineEntity, java.util.UUID>, JpaSpecificationExecutor<MedicineEntity> {
    Optional<MedicineEntity> findByCode(String code);

    List<MedicineEntity> findByCodeIn(Collection<String> codes);

    List<MedicineEntity> findByIdIn(Collection<UUID> ids);

    boolean existsByCode(String code);
}
