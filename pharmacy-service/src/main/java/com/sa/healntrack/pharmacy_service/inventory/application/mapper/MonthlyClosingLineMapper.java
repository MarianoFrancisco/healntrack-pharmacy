package com.sa.healntrack.pharmacy_service.inventory.application.mapper;

import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosingLine;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class MonthlyClosingLineMapper {

    public MonthlyClosingLine of(
            UUID closingId,
            UUID medicineId,
            int systemQty,
            int physicalQty,
            BigDecimal unitCost,
            String note) {
        return new MonthlyClosingLine(
                closingId,
                medicineId,
                systemQty,
                physicalQty,
                unitCost,
                note
        );
    }
}
