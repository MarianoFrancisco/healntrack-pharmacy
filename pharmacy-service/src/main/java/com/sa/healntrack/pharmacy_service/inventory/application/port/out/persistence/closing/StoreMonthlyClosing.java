package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.closing;

import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosing;
import com.sa.healntrack.pharmacy_service.inventory.domain.MonthlyClosingLine;

import java.util.List;

public interface StoreMonthlyClosing {
    void closeWithLines(MonthlyClosing closing, List<MonthlyClosingLine> lines);
}
