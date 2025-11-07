package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.notification.message;

public record NotificationRequestedMessage(
        String requestId,
        String to,
        String toName,
        String subject,
        String bodyHtml
) {
}
