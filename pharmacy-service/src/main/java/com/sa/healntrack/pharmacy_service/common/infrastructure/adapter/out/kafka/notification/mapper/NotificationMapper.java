package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.notification.mapper;

import com.sa.healntrack.pharmacy_service.common.application.port.out.publish_notification_created.PublishNotificationCreatedCommand;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.notification.message.NotificationRequestedMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NotificationMapper {

    public NotificationRequestedMessage toMessage(PublishNotificationCreatedCommand command) {
        return new NotificationRequestedMessage(
                command.requestId(),
                command.to(),
                command.toName(),
                command.subject(),
                command.bodyHtml()
        );
    }
}