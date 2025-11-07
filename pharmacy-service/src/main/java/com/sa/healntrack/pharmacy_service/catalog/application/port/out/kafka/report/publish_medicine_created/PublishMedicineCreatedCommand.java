package com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_created;

import java.util.UUID;

public record PublishMedicineCreatedCommand(
        UUID id,
        String name
) {
}
