package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.billing.mappers;

import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.billing.message.BillingRequestedMessage;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.billing.message.dto.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BillingRequestedEventMapper {

    public BillingRequestedMessage toMessage(PublishBillCreatedCommand command) {
        return new BillingRequestedMessage(
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
