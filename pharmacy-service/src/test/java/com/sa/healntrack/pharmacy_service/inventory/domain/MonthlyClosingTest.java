package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MonthlyClosingTest {

    private static final UUID ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID CLOSED_BY = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    @Test
    void constructor_sets_fields_and_now_timestamp() {
        MonthlyClosing c = new MonthlyClosing(2025, 10, CLOSED_BY);
        assertThat(c.getYear()).isEqualTo(2025);
        assertThat(c.getMonth()).isEqualTo(10);
        assertThat(c.getClosedBy().value()).isEqualTo(CLOSED_BY);
        assertThat(c.getClosedAt()).isPositive();
    }

    @Test
    void restore_keeps_values_and_validates_period() {
        long ts = 123456L;
        MonthlyClosing c = MonthlyClosing.restore(ID, 2024, 12, CLOSED_BY, ts);
        assertThat(c.getId().value()).isEqualTo(ID);
        assertThat(c.getClosedAt()).isEqualTo(ts);
    }

    @Test
    void invalid_month_or_year_throw() {
        assertThatThrownBy(() -> new MonthlyClosing(2025, 0, CLOSED_BY))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Mes inválido");
        assertThatThrownBy(() -> new MonthlyClosing(1999, 12, CLOSED_BY))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Año inválido");
    }
}
