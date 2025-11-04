package com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales;


import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;

import java.util.UUID;

public record GetAllSalesQuery(
        Long occurredFrom,
        Long occurredTo,
        UUID sellerId,
        SaleStatus status
) {
}
