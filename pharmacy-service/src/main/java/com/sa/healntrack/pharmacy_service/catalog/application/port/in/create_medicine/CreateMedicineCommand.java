package com.sa.healntrack.pharmacy_service.catalog.application.port.in.create_medicine;

import java.math.BigDecimal;

public record CreateMedicineCommand(
        String code,
        String name,
        String description,
        String unit,
        Integer minStock,
        BigDecimal currentPrice,
        BigDecimal currentCost
) {
}
