package com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing;

public record CreateMonthlyClosingLineCommand(
        String medicineCode,
        Integer physicalQty,
        String note
) {
}
