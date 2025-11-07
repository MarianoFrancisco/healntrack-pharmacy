package com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id;

import java.util.UUID;

public record GetSaleByIdQuery(UUID saleId) {
}
