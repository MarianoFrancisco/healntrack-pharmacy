package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization.message;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MedicationCreatedMessage(
        UUID saleId,
        UUID buyerId,
        LocalDate ocurredAt,
        BigDecimal total
) {
}
