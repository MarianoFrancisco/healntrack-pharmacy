package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.message.dto;

import java.math.BigDecimal;

public record Item(String name, int qty, BigDecimal price, BigDecimal subtotal) {
}
