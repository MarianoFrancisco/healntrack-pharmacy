package com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine;

public interface PublishMedicineSold {
    void publish(PublishMedicineSoldCommand command);
}
