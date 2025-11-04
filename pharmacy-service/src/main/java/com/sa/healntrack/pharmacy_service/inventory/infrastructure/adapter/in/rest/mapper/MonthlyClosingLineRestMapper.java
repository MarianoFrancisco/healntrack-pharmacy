package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.mapper;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_monthly_closing.CreateMonthlyClosingLineCommand;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.CreateMonthlyClosingLineDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MonthlyClosingLineRestMapper {
    public CreateMonthlyClosingLineCommand toCommand(CreateMonthlyClosingLineDTO d) {
        return new CreateMonthlyClosingLineCommand(
                d.medicineCode(),
                d.physicalQty(),
                d.note()
        );
    }
}
