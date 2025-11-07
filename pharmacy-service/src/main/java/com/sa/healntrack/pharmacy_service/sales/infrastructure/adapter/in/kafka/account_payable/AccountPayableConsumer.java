package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.kafka.account_payable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed.ConsumeAccountPayableClosed;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed.ConsumeAccountPayableClosedCommand;
import com.sa.healntrack.pharmacy_service.common.infrastructure.exception.DeserializerException;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.kafka.account_payable.mapper.AccountPayableClosedMapper;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.kafka.account_payable.message.AccountPayableClosedMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccountPayableConsumer {

    private final ConsumeAccountPayableClosed consumeSaleCompleted;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "accountpayable.closed",
            groupId = "pharmacy-service"
    )
    public void consume(ConsumerRecord<String, byte[]> record) {
        try {
            AccountPayableClosedMessage event = objectMapper.readValue(
                    record.value(),
                    AccountPayableClosedMessage.class
            );
            if (event.service() != null && !event.service().equals("MED")) {
                return;
            }
            ConsumeAccountPayableClosedCommand cmd = AccountPayableClosedMapper.toCommand(event);
            consumeSaleCompleted.handle(cmd);
        } catch (IOException ex) {
            throw new DeserializerException("SaleCompletedMessage");
        }
    }
}
