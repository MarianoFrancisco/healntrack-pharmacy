package com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_all_patients;

import java.util.UUID;

public record Patient(UUID id, String cui, String fullName) {
}
