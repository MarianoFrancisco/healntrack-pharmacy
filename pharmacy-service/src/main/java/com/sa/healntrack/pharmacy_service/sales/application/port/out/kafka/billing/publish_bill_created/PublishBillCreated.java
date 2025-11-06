package com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created;

public interface PublishBillCreated {
    void publish(PublishBillCreatedCommand event);
}
