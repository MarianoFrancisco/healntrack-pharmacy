package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.mapper;

import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosingLine;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity.MonthlyClosingLineEntity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class MonthlyClosingLineEntityMapper {

    public MonthlyClosingLineEntity toEntity(MonthlyClosingLine d) {
        return MonthlyClosingLineEntity.builder()
                .id(d.getId() != null ? d.getId().value() : UUID.randomUUID())
                .closingId(d.getClosingId().value())
                .medicineId(d.getMedicineId().value())
                .systemQty(d.getSystemQty())
                .physicalQty(d.getPhysicalQty())
                .variance(d.getVariance())
                .unitCost(d.getUnitCost())
                .varianceValue(d.getVarianceValue())
                .note(d.getNote())
                .build();
    }
}
