package com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "topics")
public class TopicsProperties {

    private PharmacyProperties pharmacy;
    private NotificationProperties notification;
    private BillingProperties billing;
}
