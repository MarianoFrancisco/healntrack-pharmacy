package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties.TopicsProperties;
import com.sa.healntrack.pharmacy_service.common.infrastructure.exception.SerializerException;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreated;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.mappers.BillMapper;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.message.BillRequestedMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillPublisher implements PublishBillCreated {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(PublishBillCreatedCommand cmd) {
        BillRequestedMessage billRequestedMessage = BillMapper.toMessage(cmd);
        try {
            byte[] eventBytes = objectMapper.writeValueAsBytes(billRequestedMessage);

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(
                            topicsProperties.billing().requested(),
                            billRequestedMessage.requestId(),
                            eventBytes
                    );
            kafkaTemplate.send(record);
        } catch (JsonProcessingException e) {
            throw new SerializerException("BillingRequestedMessage");
        }
    }
}
