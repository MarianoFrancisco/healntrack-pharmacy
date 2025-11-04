package com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id;

import com.sa.healntrack.pharmacy_service.sales.domain.Sale;

public interface GetSaleById {
    Sale handle(GetSaleByIdQuery query);
}
