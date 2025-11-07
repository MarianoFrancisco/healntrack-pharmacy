package com.sa.healntrack.pharmacy_service.common.application.port.out.publish_notification_created;

public record PublishNotificationCreatedCommand(
        String requestId,
        String to,
        String toName,
        String subject,
        String bodyHtml
) {
}
