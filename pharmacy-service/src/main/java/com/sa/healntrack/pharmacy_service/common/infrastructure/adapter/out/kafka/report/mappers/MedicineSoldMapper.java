package com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.mappers;

import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSoldCommand;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.message.MedicineSoldMessage;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.report.message.dto.TransactionType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MedicineSoldMapper {
    public MedicineSoldMessage toMessage(PublishMedicineSoldCommand command) {
        return new MedicineSoldMessage(
                command.medicineId(),
                command.service(),
                command.occurredAt(),
                TransactionType.valueOf(command.type().name()),
                command.lineTotal()
        );
    }
}
