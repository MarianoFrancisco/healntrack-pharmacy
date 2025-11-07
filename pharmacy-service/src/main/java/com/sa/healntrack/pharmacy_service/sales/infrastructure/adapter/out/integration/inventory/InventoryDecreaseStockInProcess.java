package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.integration.inventory;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStock;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStockCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.decrease_stock.InventoryDecreaseStock;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.decrease_stock.InventoryDecreaseStockCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryDecreaseStockInProcess implements InventoryDecreaseStock {

    private final DecreaseStock decreaseStock;

    @Override
    public void decrease(InventoryDecreaseStockCommand command) {
        decreaseStock.handle(new DecreaseStockCommand(command.medicineCode(), command.quantity()));
    }
}
