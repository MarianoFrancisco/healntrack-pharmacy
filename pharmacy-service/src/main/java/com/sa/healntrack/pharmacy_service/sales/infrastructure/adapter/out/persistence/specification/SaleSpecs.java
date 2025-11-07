package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.specification;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.entity.SaleEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@UtilityClass
public class SaleSpecs {

    public Specification<SaleEntity> occurredFrom(Long fromMs) {
        if (fromMs == null) return (root, cq, cb) -> cb.conjunction();
        OffsetDateTime from = OffsetDateTime.ofInstant(Instant.ofEpochMilli(fromMs), ZoneOffset.UTC);
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("occurredAt"), from);
    }

    public Specification<SaleEntity> occurredTo(Long toMs) {
        if (toMs == null) return (root, cq, cb) -> cb.conjunction();
        OffsetDateTime to = OffsetDateTime.ofInstant(Instant.ofEpochMilli(toMs), ZoneOffset.UTC);
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("occurredAt"), to);
    }

    public Specification<SaleEntity> hasSeller(UUID sellerId) {
        if (sellerId == null) return (root, cq, cb) -> cb.conjunction();
        return (root, cq, cb) -> cb.equal(root.get("sellerId"), sellerId);
    }

    public Specification<SaleEntity> hasStatus(SaleStatus status) {
        if (status == null) return (root, cq, cb) -> cb.conjunction();
        return (root, cq, cb) -> cb.equal(root.get("status"), status);
    }
}
