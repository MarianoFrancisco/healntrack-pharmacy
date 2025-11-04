package com.sa.healntrack.pharmacy_service.inventory.application.port.in.update_stock;

public record UpdateStockCommand(String medicineCode, int quantity) {
}
