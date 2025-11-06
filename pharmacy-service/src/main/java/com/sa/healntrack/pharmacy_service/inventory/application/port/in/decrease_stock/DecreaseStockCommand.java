package com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock;

public record DecreaseStockCommand(String medicineCode, int quantity) {
}
