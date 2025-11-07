package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization.mappers;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.hospitalization.publish_medication_created.PublishMedicationCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization.message.MedicationCreatedMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MedicationCreatedMapper {
    public MedicationCreatedMessage toMessage(
            PublishMedicationCreatedCommand cmd
    ) {
        return new MedicationCreatedMessage(
                cmd.saleId(),
                cmd.buyerId(),
                cmd.ocurredAt(),
                cmd.total()
        );
    }
}
