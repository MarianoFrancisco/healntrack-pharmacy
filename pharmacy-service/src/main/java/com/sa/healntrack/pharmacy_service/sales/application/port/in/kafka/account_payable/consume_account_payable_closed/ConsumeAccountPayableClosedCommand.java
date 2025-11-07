package com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed;

import java.util.UUID;

public record ConsumeAccountPayableClosedCommand(
        UUID referenceId

) {
}
