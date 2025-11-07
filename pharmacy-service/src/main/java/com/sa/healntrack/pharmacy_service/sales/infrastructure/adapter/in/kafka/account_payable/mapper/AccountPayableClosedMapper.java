package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.kafka.account_payable.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed.ConsumeAccountPayableClosedCommand;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.kafka.account_payable.message.AccountPayableClosedMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountPayableClosedMapper {
    public ConsumeAccountPayableClosedCommand toCommand(AccountPayableClosedMessage saleCompletedMessage) {
        return new ConsumeAccountPayableClosedCommand(
                saleCompletedMessage.referenceId()
        );
    }
}
