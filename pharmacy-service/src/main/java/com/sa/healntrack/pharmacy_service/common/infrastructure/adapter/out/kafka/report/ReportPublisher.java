package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSold;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSoldCommand;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.mappers.MedicineSoldMapper;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.message.MedicineSoldMessage;
import com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties.TopicsProperties;
import com.sa.healntrack.pharmacy_service.common.infrastructure.exception.SerializerException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportPublisher implements PublishMedicineSold {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final TopicsProperties reportTopics;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(PublishMedicineSoldCommand command) {
        MedicineSoldMessage medicineSoldMessage = MedicineSoldMapper.toMessage(command);
        try {
            byte[] eventBytes = objectMapper.writeValueAsBytes(medicineSoldMessage);

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(reportTopics.report().medicineSold(), eventBytes);
            kafkaTemplate.send(record);
        } catch (JsonProcessingException ex) {
            throw new SerializerException("MedicineSoldMessage");
        }
    }
}
