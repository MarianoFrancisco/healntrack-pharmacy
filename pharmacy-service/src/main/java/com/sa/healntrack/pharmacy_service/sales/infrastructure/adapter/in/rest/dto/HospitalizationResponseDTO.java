package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record HospitalizationResponseDTO(
        UUID id,
        UUID patientId,
        UUID roomId,
        LocalDate admissionDate,
        LocalDate dischargeDate,
        BigDecimal totalFee,
        List<StaffAssignmentResponseDTO> staffAssignment
) {
}
