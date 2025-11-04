package com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateSaleCommand(
        long occurredAt,
        UUID sellerId,
        List<Item> items
) {
}
