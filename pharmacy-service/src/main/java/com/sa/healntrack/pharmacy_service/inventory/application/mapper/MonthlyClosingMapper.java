package com.sa.healntrack.pharmacy_service.inventory.application.mapper;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosingCommand;
import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosing;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MonthlyClosingMapper {

    public MonthlyClosing toDomain(CreateMonthlyClosingCommand c) {
        return new MonthlyClosing(c.year(), c.month(), c.closedBy());
    }
}
