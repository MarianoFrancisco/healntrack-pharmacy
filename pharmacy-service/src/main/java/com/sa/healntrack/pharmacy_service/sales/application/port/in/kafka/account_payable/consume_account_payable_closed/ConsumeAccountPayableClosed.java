package com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed;

public interface ConsumeAccountPayableClosed {
    void handle(ConsumeAccountPayableClosedCommand command);
}
