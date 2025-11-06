package com.sa.healntrack.pharmacy_service.sales.application.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class SaleMapper {

    public Sale toDomain(CreateSaleCommand cmd, List<SaleItem> items, BigDecimal total, String status) {
        return new Sale(
                cmd.occurredAt(),
                cmd.sellerId(),
                cmd.buyerId(),
                cmd.buyerType(),
                status,
                total,
                items
        );
    }
}
