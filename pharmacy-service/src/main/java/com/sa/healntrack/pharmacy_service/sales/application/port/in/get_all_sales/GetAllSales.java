package com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales;

import com.sa.healntrack.pharmacy_service.sales.domain.Sale;

import java.util.List;

public interface GetAllSales {
    List<Sale> handle(GetAllSalesQuery query);
}
