package com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing;

public interface CreateMonthlyClosing {
    void handle(CreateMonthlyClosingCommand cmd);
}
