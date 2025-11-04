package com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale;

import com.sa.healntrack.pharmacy_service.sales.domain.Sale;

public interface CreateSale {
    Sale handle(CreateSaleCommand command);
}
