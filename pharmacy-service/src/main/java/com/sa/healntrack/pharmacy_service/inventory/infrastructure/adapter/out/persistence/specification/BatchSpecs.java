package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.specification;

import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.BatchEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.UUID;

@UtilityClass
public class BatchSpecs {

    public static Specification<BatchEntity> byMedicineId(UUID medicineId) {
        return (root, cq, cb) -> cb.equal(root.get("medicineId"), medicineId);
    }

    public static Specification<BatchEntity> onlyWithStock(Boolean onlyWithStock) {
        if (onlyWithStock == null) {
            return (root, cq, cb) -> cb.conjunction();
        }
        if (onlyWithStock) {
            return (root, cq, cb) -> cb.greaterThan(root.get("quantityOnHand"), 0);
        }
        return (root, cq, cb) -> cb.equal(root.get("quantityOnHand"), 0);
    }

    public static Specification<BatchEntity> onlyNotExpired(Boolean onlyNotExpired) {
        if (onlyNotExpired == null || !onlyNotExpired) {
            return (root, cq, cb) -> cb.conjunction();
        }
        long startOfTomorrow = startOfTomorrowUtcMillis();
        return (root, cq, cb) -> cb.or(
                cb.isNull(root.get("expirationDate")),
                cb.greaterThanOrEqualTo(root.get("expirationDate"), startOfTomorrow)
        );
    }

    private static long startOfTomorrowUtcMillis() {
        return LocalDate.now(ZoneOffset.UTC)
                .plusDays(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
    }
}
