package com.sa.healntrack.pharmacy_service.sales.application.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Money;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SaleMapper {

    public Sale toDomain(CreateSaleCommand c) {
        List<SaleItem> items = c.items().stream()
                .map(i -> new SaleItem(
                        i.medicineId(),
                        i.medicineCode(),
                        i.quantity(),
                        i.unitPrice(),
                        i.unitCost()
                ))
                .collect(Collectors.toList());

        Money total = Money.sum(items.stream()
                .map(si -> si.getUnitPrice().multiply(si.getQuantity()).value())
                .collect(Collectors.toList()));

        return new Sale(
                c.occurredAt(),
                c.sellerId(),
                SaleStatus.OPEN.name(),
                total.value(),
                items
        );
    }
}
