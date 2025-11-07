package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.kafka.account_payable.message;

import java.util.UUID;

public record AccountPayableClosedMessage(
        UUID referenceId,
        UUID patientId,
        String service
) {
}
