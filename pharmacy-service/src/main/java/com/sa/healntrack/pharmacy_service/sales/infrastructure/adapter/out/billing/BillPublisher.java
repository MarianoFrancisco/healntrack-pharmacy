package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.billing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties.TopicsProperties;
import com.sa.healntrack.pharmacy_service.common.infrastructure.exception.SerializerException;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreated;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreatedCommand;
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
    public void publish(PublishBillCreatedCommand event) {
        try {
            byte[] eventBytes = objectMapper.writeValueAsBytes(event);

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(
                            topicsProperties.getBilling().getRequested(),
                            event.requestId(),
                            eventBytes
                    );
            kafkaTemplate.send(record);
        } catch (JsonProcessingException e) {
            throw new SerializerException("BillingRequestedMessage");
        }
    }
}
