package com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.hospitalization.publish_medication_created;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PublishMedicationCreatedCommand(
        UUID saleId,
        UUID buyerId,
        LocalDate ocurredAt,
        BigDecimal total
) {
}
