package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MonthlyClosingLine")
class MonthlyClosingLineTest {

    @Test
    @DisplayName("cantidades negativas lanzan")
    void negative_quantities_throw() {
        UUID closingId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        UUID medId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

        assertThatThrownBy(() -> new MonthlyClosingLine(closingId, medId, -1, 0, BigDecimal.ZERO, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("sistema");
        assertThatThrownBy(() -> new MonthlyClosingLine(closingId, medId, 1, -2, BigDecimal.ZERO, null))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("física");
    }

    @Test
    @DisplayName("unitCost negativo lanza")
    void negative_unit_cost_throws() {
        assertThatThrownBy(() -> new MonthlyClosingLine(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                0, 0, new BigDecimal("-0.01"), null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unitario");
    }

    @Test
    @DisplayName("restore valida consistencia de varianza y valor")
    void restore_validates_consistency_of_variance_and_value() {
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        UUID closingId = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
        UUID med = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");

        MonthlyClosingLine tmp = new MonthlyClosingLine(closingId, med, 10, 15, new BigDecimal("1.50"), null);

        MonthlyClosingLine ok = MonthlyClosingLine.restore(
                id, closingId, med, 10, 15, 5, new BigDecimal("1.50"), new BigDecimal("7.50"), null
        );
        assertThat(ok.getId().value()).isEqualTo(id);

        assertThatThrownBy(() -> MonthlyClosingLine.restore(
                id, closingId, med, 10, 15, 4, new BigDecimal("1.50"), new BigDecimal("6.00"), null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Varianza inconsistente");

        assertThatThrownBy(() -> MonthlyClosingLine.restore(
                id, closingId, med, 10, 15, 5, new BigDecimal("1.50"), null, null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Valor de varianza inconsistente");
        assertThatThrownBy(() -> MonthlyClosingLine.restore(
                id, closingId, med, 10, 15, 5, new BigDecimal("1.50"), new BigDecimal("7.40"), null
        )).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Valor de varianza inconsistente");
    }
}
