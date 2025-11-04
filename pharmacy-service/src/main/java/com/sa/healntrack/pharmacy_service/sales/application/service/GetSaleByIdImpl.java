package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.sales.application.exception.SaleNotFoundException;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id.GetSaleById;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id.GetSaleByIdQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.FindSales;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSaleByIdImpl implements GetSaleById {

    private final FindSales findSales;

    @Override
    public Sale handle(GetSaleByIdQuery query) {
        return findSales.findById(query.saleId())
                .orElseThrow(() -> new SaleNotFoundException(query.saleId().toString()));
    }
}
