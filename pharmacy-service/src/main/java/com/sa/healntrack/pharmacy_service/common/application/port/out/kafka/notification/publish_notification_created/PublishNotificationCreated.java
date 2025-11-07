package com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.notification.publish_notification_created;

public interface PublishNotificationCreated {
    void publish(PublishNotificationCreatedCommand command);
}
