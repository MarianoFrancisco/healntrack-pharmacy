package com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.hospitalization.get_all_hospitalizations;

import java.util.UUID;

public record Hospitalization(UUID id, UUID patientId) {
}
