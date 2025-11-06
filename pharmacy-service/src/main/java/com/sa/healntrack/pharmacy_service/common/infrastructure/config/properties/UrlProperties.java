package com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "urls")
public record UrlProperties(String patientService, String employeeService) {
}
