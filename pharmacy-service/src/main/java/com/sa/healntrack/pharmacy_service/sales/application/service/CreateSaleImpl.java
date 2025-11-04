package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.sales.application.mapper.SaleMapper;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.InventaryUpdateStock;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.StoreSale;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateSaleImpl implements CreateSale {

    private final StoreSale storeSale;
    private final FindMedicines findMedicines;
    private final InventaryUpdateStock inventaryUpdateStock;

    @Override
    public Sale handle(CreateSaleCommand command) {
        for (Item item : command.items()) {
            if (item.medicineId() == null) {
                findMedicines.findByCode(item.medicineCode())
                        .map(m -> m.getId().value())
                        .orElseThrow(() -> new MedicineNotFoundException(item.medicineCode()));
            }
        }
        Sale sale = SaleMapper.toDomain(command);

        for (SaleItem it : sale.getItems()) {
            inventaryUpdateStock.consume(it.getMedicineCode().value(), it.getQuantity());
        }
        return storeSale.save(sale);
    }
}
