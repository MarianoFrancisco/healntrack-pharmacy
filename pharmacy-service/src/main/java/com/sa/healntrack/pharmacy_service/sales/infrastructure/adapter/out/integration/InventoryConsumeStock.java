package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.integration;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.update_stock.UpdateStock;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.update_stock.UpdateStockCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.InventaryUpdateStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryConsumeStock implements InventaryUpdateStock {

    private final UpdateStock updateStock;

    @Override
    public void consume(String medicineCode, int quantity) {
        UpdateStockCommand command = new UpdateStockCommand(medicineCode, quantity);
        updateStock.handle(command);
    }
}
