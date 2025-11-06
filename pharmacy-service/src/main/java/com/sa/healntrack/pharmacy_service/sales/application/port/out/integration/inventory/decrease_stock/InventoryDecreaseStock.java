package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.decrease_stock;

public interface InventoryDecreaseStock {
    void decrease(InventoryDecreaseStockCommand command);
}
