package com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_created;

public interface PublishMedicineCreated {
    void publish(PublishMedicineCreatedCommand command);
}
