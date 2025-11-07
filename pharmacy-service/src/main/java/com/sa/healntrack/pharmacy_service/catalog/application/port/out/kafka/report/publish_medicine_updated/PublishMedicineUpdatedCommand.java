package com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_updated;

import java.util.UUID;

public record PublishMedicineUpdatedCommand(
        UUID id,
        String name
) {
}
