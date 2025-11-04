package com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_batch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBatchCommand(
        String medicineCode,
        LocalDate expirationDate,
        Integer purchasedQuantity,
        UUID purchasedBy
) {
}
