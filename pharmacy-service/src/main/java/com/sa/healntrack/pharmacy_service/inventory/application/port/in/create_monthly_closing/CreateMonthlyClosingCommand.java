package com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing;

import java.util.List;
import java.util.UUID;

public record CreateMonthlyClosingCommand(
        int year,
        int month,
        UUID closedBy,
        List<CreateMonthlyClosingLineCommand> lines
) {
}
