package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_created.PublishMedicineCreated;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_created.PublishMedicineCreatedCommand;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_updated.PublishMedicineUpdated;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_updated.PublishMedicineUpdatedCommand;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.mappers.MedicineCreatedMapper;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.mappers.MedicineUpdatedMapper;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.message.MedicineCreatedMessage;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.message.MedicineUpdatedMessage;
import com.sa.healntrack.pharmacy_service.common.infrastructure.config.properties.TopicsProperties;
import com.sa.healntrack.pharmacy_service.common.infrastructure.exception.SerializerException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogReportPublisher implements PublishMedicineCreated, PublishMedicineUpdated {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final TopicsProperties reportTopics;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(PublishMedicineCreatedCommand command) {
        MedicineCreatedMessage notificationRequestedMessage = MedicineCreatedMapper.toMessage(command);
        try {
            byte[] eventBytes = objectMapper.writeValueAsBytes(notificationRequestedMessage);

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(reportTopics.report().medicineCreated(), eventBytes);
            kafkaTemplate.send(record);
        } catch (JsonProcessingException ex) {
            throw new SerializerException("MedicineCreatedMessage");
        }
    }

    @Override
    public void publish(PublishMedicineUpdatedCommand command) {
        MedicineUpdatedMessage medicineUpdatedMessage = MedicineUpdatedMapper.toMessage(command);
        try {
            byte[] eventBytes = objectMapper.writeValueAsBytes(medicineUpdatedMessage);

            ProducerRecord<String, byte[]> record =
                    new ProducerRecord<>(reportTopics.report().medicineUpdated(), eventBytes);
            kafkaTemplate.send(record);
        } catch (JsonProcessingException ex) {
            throw new SerializerException("MedicineUpdatedMessage");
        }
    }
}
