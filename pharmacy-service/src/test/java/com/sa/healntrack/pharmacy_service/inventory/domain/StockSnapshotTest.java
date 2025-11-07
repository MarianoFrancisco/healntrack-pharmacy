package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("StockSnapshot")
class StockSnapshotTest {

    @Test
    @DisplayName("constructor normaliza no negativo y timestamp now")
    void constructor_sets_non_negative_and_now_timestamp() {
        UUID med = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        StockSnapshot s = new StockSnapshot(med, -5);

        assertThat(s.getMedicineId().value()).isEqualTo(med);
        assertThat(s.getTotalQuantity().value()).isEqualTo(0);
        assertThat(s.getUpdatedAt()).isPositive();
    }

    @Test
    @DisplayName("restore conserva timestamp")
    void restore_keeps_timestamp() {
        UUID med = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        StockSnapshot s = StockSnapshot.restore(med, 12, 123L);
        assertThat(s.getTotalQuantity().value()).isEqualTo(12);
        assertThat(s.getUpdatedAt()).isEqualTo(123L);
    }

    @Test
    @DisplayName("applyDelta actualiza cantidad y evita negativo")
    void applyDelta_updates_quantity_and_prevents_negative() {
        UUID med = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
        StockSnapshot s = new StockSnapshot(med, 10);
        long before = s.getUpdatedAt();

        s.applyDelta(-3);
        assertThat(s.getTotalQuantity().value()).isEqualTo(7);
        assertThat(s.getUpdatedAt()).isGreaterThanOrEqualTo(before);

        assertThatThrownBy(() -> s.applyDelta(-8))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("quedaría negativo");
    }
}
