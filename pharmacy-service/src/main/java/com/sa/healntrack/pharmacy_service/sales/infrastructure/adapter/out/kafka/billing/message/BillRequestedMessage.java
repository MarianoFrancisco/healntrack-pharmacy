package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.message;


import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.message.dto.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BillRequestedMessage(
        String requestId,
        String subject,
        String templateKey,
        String title,
        String description,
        String email,
        String taxId,
        String name,
        List<Item> items,
        BigDecimal total,
        LocalDate date
) {
}
