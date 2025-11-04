package com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindMedicines {
    Optional<Medicine> findByCode(String code);

    Optional<Medicine> findById(UUID id);

    boolean existsByCode(String code);

    List<Medicine> search(String searchTerm, Boolean isActive);
}
