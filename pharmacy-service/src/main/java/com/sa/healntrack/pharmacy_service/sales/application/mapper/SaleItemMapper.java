package com.sa.healntrack.pharmacy_service.sales.application.mapper;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class SaleItemMapper {

    public List<SaleItem> toDomain(CreateSaleCommand c, Map<String, Medicine> medByCode) {

        return c.items().stream()
                .map(i -> {
                    Medicine med = medByCode.get(i.medicineCode());

                    return new SaleItem(
                            med.getId().value(),
                            med.getCode().value(),
                            i.quantity(),
                            med.getCurrentPrice(),
                            med.getCurrentCost()
                    );
                })
                .toList();
    }
}
