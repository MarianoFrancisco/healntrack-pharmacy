package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.hospitalization;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.hospitalization.get_all_hospitalizations.GetAllHospitalizations;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.hospitalization.get_all_hospitalizations.Hospitalization;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.HospitalizationResponseDTO;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.hospitalization.mapper.HospitalizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesHospitalizationApi implements GetAllHospitalizations {

    private final RestClient hospitalizationRestClient;

    @Override
    public List<Hospitalization> getAll() {
        List<HospitalizationResponseDTO> response = hospitalizationRestClient.get()
                .uri("/api/v1/hospitalizations")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response == null) {
            throw new RestClientException("Hospitalizaciones no encontradas.");
        }

        return HospitalizationMapper.toApplication(response);
    }
}
