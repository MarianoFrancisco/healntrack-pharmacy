package com.sa.healntrack.pharmacy_service.inventory.application.port.in.update_stock;

public interface UpdateStock {
    void handle(UpdateStockCommand cmd);
}
