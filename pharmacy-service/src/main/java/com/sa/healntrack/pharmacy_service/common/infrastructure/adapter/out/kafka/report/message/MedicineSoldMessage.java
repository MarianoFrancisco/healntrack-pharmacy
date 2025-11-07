package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.message;

import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.message.dto.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MedicineSoldMessage(

        UUID medicineId,
        String service,
        LocalDate occurredAt,
        TransactionType type,
        BigDecimal lineTotal

) {
}