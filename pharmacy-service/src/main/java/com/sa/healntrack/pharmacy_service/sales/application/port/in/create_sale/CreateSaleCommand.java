package com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale;

import java.util.List;
import java.util.UUID;

public record CreateSaleCommand(
        long occurredAt,
        UUID sellerId,
        UUID buyerId,
        String buyerType,
        List<Item> items
) {
}