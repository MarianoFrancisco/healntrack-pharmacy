package com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "topics")
public record TopicsProperties(
        PharmacyProperties pharmacy,
        NotificationProperties notification,
        BillingProperties billing,
        HospitalizationProperties hospitalization
) {
}
