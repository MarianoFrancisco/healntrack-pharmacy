package com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PublishBillCreatedCommand(
        String requestId,
        String subject,
        String templateKey,
        String title,
        String description,
        String email,
        String taxId,
        String name,
        List<ItemBill> items,
        BigDecimal total,
        LocalDate date
) {
}
