package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.mappers;

import com.sa.healntrack.pharmacy_service.catalog.application.port.out.kafka.report.publish_medicine_updated.PublishMedicineUpdatedCommand;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.kafka.report.message.MedicineUpdatedMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MedicineUpdatedMapper {
    public MedicineUpdatedMessage toMessage(PublishMedicineUpdatedCommand command) {
        return new MedicineUpdatedMessage(
                command.id(),
                command.name()
        );
    }
}
