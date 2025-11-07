package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class StockSnapshotTest {

    private static final UUID MED = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @Test
    void constructor_sets_non_negative_and_now_timestamp() {
        StockSnapshot s = new StockSnapshot(MED, -5);
        assertThat(s.getMedicineId().value()).isEqualTo(MED);
        assertThat(s.getTotalQuantity().value()).isEqualTo(0);
        assertThat(s.getUpdatedAt()).isPositive();
    }

    @Test
    void restore_keeps_timestamp() {
        StockSnapshot s = StockSnapshot.restore(MED, 12, 123L);
        assertThat(s.getTotalQuantity().value()).isEqualTo(12);
        assertThat(s.getUpdatedAt()).isEqualTo(123L);
    }

    @Test
    void applyDelta_updates_quantity_and_prevents_negative() {
        StockSnapshot s = new StockSnapshot(MED, 10);
        long before = s.getUpdatedAt();
        s.applyDelta(-3);
        assertThat(s.getTotalQuantity().value()).isEqualTo(7);
        assertThat(s.getUpdatedAt()).isGreaterThanOrEqualTo(before);
        assertThatThrownBy(() -> s.applyDelta(-8))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("quedaría negativo");
    }
}
