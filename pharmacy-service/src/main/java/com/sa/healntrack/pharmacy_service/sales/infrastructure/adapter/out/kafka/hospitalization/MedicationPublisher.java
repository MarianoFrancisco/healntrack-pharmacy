package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties.TopicsProperties;
import com.sa.healntrack.pharmacy_service.common.infrastructure.exception.SerializerException;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.hospitalization.publish_medication_created.PublishMedicationCreated;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.hospitalization.publish_medication_created.PublishMedicationCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization.mappers.MedicationMapper;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization.message.MedicationCreatedMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicationPublisher implements PublishMedicationCreated {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(PublishMedicationCreatedCommand cmd) {
        MedicationCreatedMessage medicationCreatedMessage = MedicationMapper.toMessage(cmd);
        try {
            byte[] eventBytes = objectMapper.writeValueAsBytes(medicationCreatedMessage);

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(
                            topicsProperties.hospitalization().medicationCreated(),
                            eventBytes
                    );
            kafkaTemplate.send(record);
        } catch (JsonProcessingException e) {
            throw new SerializerException("MedicationCreatedMessage");
        }
    }
}
