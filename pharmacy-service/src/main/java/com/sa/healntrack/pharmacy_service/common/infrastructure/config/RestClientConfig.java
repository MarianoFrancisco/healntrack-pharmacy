package com.sa.healntrack.pharmacy_service.common.infrastructure.config;

import com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties.UrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final UrlProperties urlProperties;

    @Bean
    public RestClient patientRestClient() {
        return RestClient.builder()
                .baseUrl(urlProperties.patientService())
                .build();
    }

    @Bean
    public RestClient employeeRestClient() {
        return RestClient.builder()
                .baseUrl(urlProperties.employeeService())
                .build();
    }
}
