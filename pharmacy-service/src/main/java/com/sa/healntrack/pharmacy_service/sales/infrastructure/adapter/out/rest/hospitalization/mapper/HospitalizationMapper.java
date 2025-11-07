package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.hospitalization.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.hospitalization.get_all_hospitalizations.Hospitalization;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.HospitalizationResponseDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class HospitalizationMapper {

    public List<Hospitalization> toApplication(List<HospitalizationResponseDTO> responses) {
        return responses.stream()
                .map(HospitalizationMapper::toApplication)
                .toList();
    }

    public Hospitalization toApplication(HospitalizationResponseDTO response) {
        return new Hospitalization(
                response.id(),
                response.patientId()
        );
    }
}
