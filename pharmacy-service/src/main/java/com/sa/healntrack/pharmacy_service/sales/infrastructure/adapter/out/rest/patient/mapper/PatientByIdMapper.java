package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.PatientById;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.PatientResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PatientByIdMapper {

    public PatientById toApplication(PatientResponseDTO response) {
        return new PatientById(
                response.email(),
                response.cui(),
                response.fullName()
        );
    }
}
