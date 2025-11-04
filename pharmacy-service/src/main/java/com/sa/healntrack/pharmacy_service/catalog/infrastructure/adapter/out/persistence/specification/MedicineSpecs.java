package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.specification;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineStatus;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.entity.MedicineEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public final class MedicineSpecs {

    public Specification<MedicineEntity> hasStatus(Boolean isActive) {
        if (isActive == null) {
            return (root, cq, cb) -> cb.conjunction();
        }
        MedicineStatus status = isActive ? MedicineStatus.ACTIVE : MedicineStatus.INACTIVE;
        return (root, cq, cb) -> cb.equal(root.get("status"), status);
    }

    public Specification<MedicineEntity> codeContains(String term) {
        if (term == null || term.isBlank()) {
            return (root, cq, cb) -> cb.conjunction();
        }
        String like = "%" + term.trim().toUpperCase() + "%";
        return (root, cq, cb) -> cb.like(cb.upper(root.get("code")), like);
    }

    public Specification<MedicineEntity> nameContains(String term) {
        if (term == null || term.isBlank()) {
            return (root, cq, cb) -> cb.conjunction();
        }
        String like = "%" + term.trim().toUpperCase() + "%";
        return (root, cq, cb) -> cb.like(cb.upper(root.get("name")), like);
    }

    public Specification<MedicineEntity> codeOrNameContains(String term) {
        if (term == null || term.isBlank()) {
            return (root, cq, cb) -> cb.conjunction();
        }
        return Specification.anyOf(codeContains(term), nameContains(term));
    }
}
