package com.sa.healntrack.pharmacy_service.catalog.application.port.in.update_medicine;

import java.math.BigDecimal;

public record UpdateMedicineCommand(
        String name,
        String description,
        String unit,
        Integer minStock,
        BigDecimal currentPrice,
        BigDecimal currentCost
) {
}
