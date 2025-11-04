package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSalesQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.FindSales;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllSalesImpl implements GetAllSales {

    private final FindSales findSales;

    @Override
    public List<Sale> handle(GetAllSalesQuery q) {
        String status = q.status() != null ? q.status().name() : null;
        return findSales.search(q.occurredFrom(), q.occurredTo(), q.sellerId(), status);
    }
}
