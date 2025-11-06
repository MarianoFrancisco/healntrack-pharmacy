package com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;
import java.util.Set;
import java.util.Optional;

public interface FindMedicines {
    Optional<Medicine> findByCode(String code);

    List<Medicine> findByCodes(Set<String> code);

    boolean existsByCode(String code);

    List<Medicine> search(String searchTerm, Boolean isActive);
}
