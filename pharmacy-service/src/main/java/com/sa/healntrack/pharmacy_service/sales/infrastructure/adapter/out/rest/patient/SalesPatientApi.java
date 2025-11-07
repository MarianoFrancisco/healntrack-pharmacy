package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_all_patients.GetAllPatients;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_all_patients.Patient;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.GetPatientById;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.GetPatientByIdQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.PatientById;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.PatientResponseDTO;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient.mapper.PatientByIdMapper;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesPatientApi implements GetPatientById, GetAllPatients {

    private final RestClient patientRestClient;

    @Override
    public PatientById get(GetPatientByIdQuery query) {
        PatientResponseDTO response = patientRestClient.get()
                .uri("/api/v1/patients/{id}", query.patientId())
                .retrieve()
                .body(PatientResponseDTO.class);

        if (response == null) {
            throw new RestClientException("Paciente con identicador " + query.patientId() + " no encontrado.");
        }

        return PatientByIdMapper.toApplication(response);
    }

    @Override
    public List<Patient> getAll() {
        List<PatientResponseDTO> response = patientRestClient.get()
                .uri("/api/v1/patients")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response == null) {
            throw new RestClientException("Pacientes no encontrados.");
        }

        return PatientMapper.toApplication(response);
    }
}
