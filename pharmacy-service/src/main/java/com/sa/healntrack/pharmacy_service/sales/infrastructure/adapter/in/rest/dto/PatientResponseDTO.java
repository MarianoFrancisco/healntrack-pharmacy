package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PatientResponseDTO(

        UUID id,
        String cui,
        String fullName,
        LocalDate birthDate,
        String gender,
        String address,
        String email,
        String phoneNumber,
        String emergencyPhoneNumber

) {
}