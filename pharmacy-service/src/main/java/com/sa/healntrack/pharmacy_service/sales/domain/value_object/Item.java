package com.sa.healntrack.pharmacy_service.sales.domain.value_object;

import java.math.BigDecimal;
import java.util.UUID;

public record Item(
        UUID medicineId,
        String medicineCode,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal unitCost
) {
}