package com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_updated;

public interface PublishMedicineUpdated {
    void publish(PublishMedicineUpdatedCommand command);
}
