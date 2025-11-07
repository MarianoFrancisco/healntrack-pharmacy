package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MonthlyClosing")
class MonthlyClosingTest {

    @Test
    @DisplayName("constructor setea campos y timestamp now")
    void constructor_sets_fields_and_now_timestamp() {
        UUID closedBy = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        MonthlyClosing c = new MonthlyClosing(2025, 10, closedBy);

        assertThat(c.getYear()).isEqualTo(2025);
        assertThat(c.getMonth()).isEqualTo(10);
        assertThat(c.getClosedBy().value()).isEqualTo(closedBy);
        assertThat(c.getClosedAt()).isPositive();
    }

    @Test
    @DisplayName("restore conserva valores y valida periodo")
    void restore_keeps_values_and_validates_period() {
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        UUID closedBy = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        long ts = 123456L;

        MonthlyClosing c = MonthlyClosing.restore(id, 2024, 12, closedBy, ts);

        assertThat(c.getId().value()).isEqualTo(id);
        assertThat(c.getClosedAt()).isEqualTo(ts);
    }

    @Test
    @DisplayName("mes o año inválidos lanzan")
    void invalid_month_or_year_throw() {
        assertThatThrownBy(() -> new MonthlyClosing(2025, 0, UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Mes inválido");
        assertThatThrownBy(() -> new MonthlyClosing(1999, 12, UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Año inválido");
    }
}
