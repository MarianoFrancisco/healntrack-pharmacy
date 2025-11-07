package com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales;

import java.util.UUID;

public record GetAllSalesQuery(
        Long occurredFrom,
        Long occurredTo,
        UUID sellerId,
        String status
) {
}
