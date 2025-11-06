package com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id;

public interface GetPatientById {
    Patient get(GetPatientByIdQuery patientId);
}
