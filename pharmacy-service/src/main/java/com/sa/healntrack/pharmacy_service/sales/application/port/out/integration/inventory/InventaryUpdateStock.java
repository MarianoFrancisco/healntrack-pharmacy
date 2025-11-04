package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory;

public interface InventaryUpdateStock {
    void consume(String medicineCode, int quantity);
}
