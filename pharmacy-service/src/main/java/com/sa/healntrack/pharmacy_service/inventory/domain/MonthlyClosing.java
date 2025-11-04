package com.sa.healntrack.pharmacy_service.inventory.domain;

import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.ClosedById;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.MonthlyClosingId;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class MonthlyClosing {
    private MonthlyClosingId id;
    private int year;
    private int month;
    private ClosedById closedBy;
    private long closedAt;

    public MonthlyClosing(int year, int month, UUID closedBy) {
        validatePeriod(year, month);
        this.year = year;
        this.month = month;
        this.closedBy = closedBy != null ? new ClosedById(closedBy) : null;
        this.closedAt = Instant.now().toEpochMilli();
    }

    public static MonthlyClosing restore(UUID id, int year, int month, UUID closedBy, long closedAt) {
        validatePeriod(year, month);
        MonthlyClosing c = new MonthlyClosing(year, month, closedBy);
        c.id = new MonthlyClosingId(id);
        c.closedAt = closedAt;
        return c;
    }

    private static void validatePeriod(int year, int month) {
        if (month < 1 || month > 12) throw new IllegalArgumentException("Mes inválido");
        if (year < 2000 || year > 3000) throw new IllegalArgumentException("Año inválido");
    }
}
