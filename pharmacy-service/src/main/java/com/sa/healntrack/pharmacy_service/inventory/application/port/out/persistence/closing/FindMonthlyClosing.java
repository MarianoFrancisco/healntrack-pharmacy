package com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.closing;

public interface FindMonthlyClosing {
    boolean existsByYearMonth(int year, int month);
}
