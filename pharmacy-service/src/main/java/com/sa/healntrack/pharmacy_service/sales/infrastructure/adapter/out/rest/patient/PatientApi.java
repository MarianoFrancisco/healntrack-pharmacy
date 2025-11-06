package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.GetPatientById;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.GetPatientByIdQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.Patient;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.PatientResponseDTO;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class PatientApi implements GetPatientById {

    private final RestClient patientRestClient;

    @Override
    public Patient get(GetPatientByIdQuery query) {
        PatientResponseDTO response = patientRestClient.get()
                .uri("/api/v1/patients/{id}", query.patientId())
                .retrieve()
                .body(PatientResponseDTO.class);

        if (response == null) {
            throw new RestClientException("Paciente con identicador " + query.patientId() + " no encontrado.");
        }

        return PatientMapper.toApplication(response);
    }
}
