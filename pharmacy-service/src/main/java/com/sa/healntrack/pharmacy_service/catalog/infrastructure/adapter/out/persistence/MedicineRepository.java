package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence;

import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.*;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.entity.MedicineEntity;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.mapper.MedicineEntityMapper;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.repository.MedicineJpaRepository;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.specification.MedicineSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MedicineRepository implements FindMedicines, StoreMedicine {

    private final MedicineJpaRepository jpa;

    @Override
    public Optional<Medicine> findByCode(String code) {
        return jpa.findByCode(code).map(MedicineEntityMapper::toDomain);
    }

    @Override
    public List<Medicine> findByCodes(Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return List.of();
        }
        Set<String> normalized = codes.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> s.toUpperCase(Locale.ROOT))
                .collect(Collectors.toSet());

        if (normalized.isEmpty()) {
            return List.of();
        }

        return jpa.findByCodeIn(normalized).stream()
                .map(MedicineEntityMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return jpa.existsByCode(code);
    }

    @Override
    public List<Medicine> search(String searchTerm, Boolean isActive) {
        String term = (searchTerm == null || searchTerm.isBlank()) ? null : searchTerm.trim();

        Specification<MedicineEntity> spec = Specification.allOf(
                MedicineSpecs.codeOrNameContains(term),
                MedicineSpecs.hasStatus(isActive)
        );

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return jpa.findAll(spec, sort).stream().map(MedicineEntityMapper::toDomain).toList();
    }

    @Override
    public Medicine save(Medicine medicine) {
        MedicineEntity saved = jpa.save(MedicineEntityMapper.toEntity(medicine));
        return MedicineEntityMapper.toDomain(saved);
    }
}
