package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MonthlyClosingLineTest {

    private static final UUID CLOSING_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID MED_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final UUID LINE_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

    @Test
    void negative_quantities_throw() {
        assertThatThrownBy(() -> new MonthlyClosingLine(CLOSING_ID, MED_ID, -1, 0, BigDecimal.ZERO, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("sistema");
        assertThatThrownBy(() -> new MonthlyClosingLine(CLOSING_ID, MED_ID, 1, -2, BigDecimal.ZERO, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("física");
    }

    @Test
    void negative_unit_cost_throws() {
        assertThatThrownBy(() -> new MonthlyClosingLine(
                CLOSING_ID, MED_ID, 0, 0, new BigDecimal("-0.01"), null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unitario");
    }

    @Test
    void restore_validates_consistency_of_variance_and_value() {
        MonthlyClosingLine tmp = new MonthlyClosingLine(CLOSING_ID, MED_ID, 10, 15, new BigDecimal("1.50"), null);

        MonthlyClosingLine ok = MonthlyClosingLine.restore(
                LINE_ID, CLOSING_ID, MED_ID, 10, 15, 5, new BigDecimal("1.50"), new BigDecimal("7.50"), null
        );
        assertThat(ok.getId().value()).isEqualTo(LINE_ID);

        assertThatThrownBy(() -> MonthlyClosingLine.restore(
                LINE_ID, CLOSING_ID, MED_ID, 10, 15, 4, new BigDecimal("1.50"), new BigDecimal("6.00"), null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Varianza inconsistente");

        assertThatThrownBy(() -> MonthlyClosingLine.restore(
                LINE_ID, CLOSING_ID, MED_ID, 10, 15, 5, new BigDecimal("1.50"), null, null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Valor de varianza inconsistente");
        assertThatThrownBy(() -> MonthlyClosingLine.restore(
                LINE_ID, CLOSING_ID, MED_ID, 10, 15, 5, new BigDecimal("1.50"), new BigDecimal("7.40"), null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Valor de varianza inconsistente");
    }
}
