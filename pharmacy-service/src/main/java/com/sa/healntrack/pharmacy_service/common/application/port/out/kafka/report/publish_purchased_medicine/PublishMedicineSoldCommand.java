package com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PublishMedicineSoldCommand(
        UUID medicineId,
        String service,
        LocalDate occurredAt,
        ReportTransactionType type,
        BigDecimal lineTotal
) {
}
