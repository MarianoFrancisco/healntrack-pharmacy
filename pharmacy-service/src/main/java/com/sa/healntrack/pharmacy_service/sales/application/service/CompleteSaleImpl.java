package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed.ConsumeAccountPayableClosed;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.kafka.account_payable.consume_account_payable_closed.ConsumeAccountPayableClosedCommand;
import com.sa.healntrack.pharmacy_service.sales.application.exception.SaleAlreadyCompletedException;
import com.sa.healntrack.pharmacy_service.sales.application.exception.SaleNotFoundException;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.FindSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.StoreSale;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CompleteSaleImpl implements ConsumeAccountPayableClosed {

    private final FindSales findSales;
    private final StoreSale storeSale;

    @Override
    public void handle(ConsumeAccountPayableClosedCommand command) {
        UUID id = command.referenceId();
        Sale sale = findSales.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id.toString()));
        if (sale.getStatus() == SaleStatus.COMPLETED) {
            throw new SaleAlreadyCompletedException();
        }

        sale.complete();
        storeSale.save(sale);
    }
}
