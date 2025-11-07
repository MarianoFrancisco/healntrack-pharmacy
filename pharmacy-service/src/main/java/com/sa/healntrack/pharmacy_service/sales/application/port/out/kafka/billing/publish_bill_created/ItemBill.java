package com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created;

import java.math.BigDecimal;

public record ItemBill(String name, int qty, BigDecimal price, BigDecimal subtotal) {
}