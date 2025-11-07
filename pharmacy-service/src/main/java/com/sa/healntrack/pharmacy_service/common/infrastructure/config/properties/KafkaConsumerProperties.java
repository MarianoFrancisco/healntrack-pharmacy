package com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.consumer")
public record KafkaConsumerProperties(
        String groupId
) {
}
