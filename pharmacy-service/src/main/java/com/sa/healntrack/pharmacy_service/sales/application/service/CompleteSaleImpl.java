package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.sales.application.exception.SaleAlreadyCompletedException;
import com.sa.healntrack.pharmacy_service.sales.application.exception.SaleNotFoundException;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.complete_sale.CompleteSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.complete_sale.CompleteSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.FindSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.StoreSale;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CompleteSaleImpl implements CompleteSale {

    private final FindSales findSales;
    private final StoreSale storeSale;

    @Override
    public void handle(CompleteSaleCommand command) {
        Sale sale = findSales.findById(command.saleId())
                .orElseThrow(() -> new SaleNotFoundException(command.saleId().toString()));
        if (sale.getStatus() == SaleStatus.COMPLETED) {
            throw new SaleAlreadyCompletedException();
        }

        sale.complete();
        storeSale.save(sale);
    }
}
