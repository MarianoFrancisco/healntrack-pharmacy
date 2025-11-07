package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.message;

import java.util.UUID;

public record MedicineCreatedMessage(

        UUID id,
        String name

) {
}
