package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.mappers;

import com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_created.PublishMedicineCreatedCommand;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.message.MedicineCreatedMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MedicineCreatedMapper {
    public MedicineCreatedMessage toMessage(PublishMedicineCreatedCommand command) {
        return new MedicineCreatedMessage(
                command.id(),
                command.name()
        );
    }
}
