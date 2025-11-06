package com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale;

public record Item(
        String medicineCode,
        int quantity
) {
}