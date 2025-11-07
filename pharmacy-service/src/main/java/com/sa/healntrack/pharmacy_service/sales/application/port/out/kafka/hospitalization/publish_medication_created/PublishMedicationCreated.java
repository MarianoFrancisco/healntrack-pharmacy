package com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.hospitalization.publish_medication_created;

public interface PublishMedicationCreated {
    void publish(PublishMedicationCreatedCommand event);
}
