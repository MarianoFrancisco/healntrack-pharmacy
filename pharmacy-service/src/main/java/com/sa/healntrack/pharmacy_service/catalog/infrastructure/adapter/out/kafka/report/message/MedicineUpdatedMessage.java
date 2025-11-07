package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.message;

import java.util.UUID;

public record MedicineUpdatedMessage(

        UUID id,
        String name

) {
}