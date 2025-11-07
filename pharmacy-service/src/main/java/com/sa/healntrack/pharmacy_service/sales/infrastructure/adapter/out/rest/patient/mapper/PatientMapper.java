package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.patient.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_all_patients.Patient;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.PatientResponseDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PatientMapper {
    public List<Patient> toApplication(List<PatientResponseDTO> response) {
        return response.stream()
                .map(PatientMapper::toApplication)
                .toList();
    }

    public Patient toApplication(PatientResponseDTO response) {
        return new Patient(
                response.id(),
                response.cui(),
                response.fullName()
        );
    }
}
