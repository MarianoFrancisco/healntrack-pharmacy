package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.decrease_stock;

public record InventoryDecreaseStockCommand(String medicineCode, int quantity) {
}
