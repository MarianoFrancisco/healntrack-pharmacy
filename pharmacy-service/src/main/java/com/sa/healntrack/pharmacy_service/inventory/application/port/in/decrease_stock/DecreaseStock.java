package com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock;

public interface DecreaseStock {
    void handle(DecreaseStockCommand cmd);
}
