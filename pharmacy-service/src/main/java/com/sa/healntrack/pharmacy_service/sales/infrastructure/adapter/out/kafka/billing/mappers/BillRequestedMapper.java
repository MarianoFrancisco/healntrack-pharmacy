package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.mappers;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.message.BillRequestedMessage;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.message.dto.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BillRequestedMapper {

    public BillRequestedMessage toMessage(PublishBillCreatedCommand command) {
        return new BillRequestedMessage(
                command.requestId(),
                command.subject(),
                command.templateKey(),
                command.title(),
                command.description(),
                command.email(),
                command.taxId(),
                command.name(),
                command.items().stream().map(
                        item -> new Item(
                                item.name(),
                                item.qty(),
                                item.price(),
                                item.subtotal()
                        )
                ).toList(),
                command.total(),
                command.date()
        );
    }
}
