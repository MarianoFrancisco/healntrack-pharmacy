package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class BatchTest {

    private static final UUID MED = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID BUYER = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final UUID BATCH_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

    @Test
    void constructor_sets_quantities_and_timestamps() {
        Batch b = new Batch(MED, LocalDate.now().plusMonths(6), 10, new BigDecimal("2.50"), BUYER);
        assertThat(b.getPurchasedQuantity().value()).isEqualTo(10);
        assertThat(b.getQuantityOnHand().value()).isEqualTo(10);
        assertThat(b.getPurchasePrice().value()).isEqualByComparingTo("2.50");
        assertThat(b.getCreatedAt()).isPositive();
        assertThat(b.getUpdatedAt()).isPositive();
    }

    @Test
    void restore_validates_quantity_on_hand_not_negative_nor_greater_than_purchased() {
        Batch ok = Batch.restore(BATCH_ID, MED, LocalDate.now(), 10, 5, new BigDecimal("3.00"),
                BUYER, 100L, 200L);
        assertThat(ok.getQuantityOnHand().value()).isEqualTo(5);

        assertThatThrownBy(() -> Batch.restore(BATCH_ID, MED, LocalDate.now(), 10, -1, new BigDecimal("3.00"),
                BUYER, 100L, 200L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cantidad en mano inválida");

        assertThatThrownBy(() -> Batch.restore(BATCH_ID, MED, LocalDate.now(), 10, 11, new BigDecimal("3.00"),
                BUYER, 100L, 200L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cantidad en mano inválida");
    }

    @Test
    void consume_decreases_stock_and_updates_timestamp_and_prevents_over_consume() {
        Batch b = new Batch(MED, LocalDate.now().plusDays(1),
                5, new BigDecimal("1.00"), BUYER);

        long before = b.getUpdatedAt();
        int consumed = b.consume(3);
        assertThat(consumed).isEqualTo(3);
        assertThat(b.getQuantityOnHand().value()).isEqualTo(2);
        assertThat(b.getUpdatedAt()).isGreaterThanOrEqualTo(before);

        assertThatThrownBy(() -> b.consume(5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Stock insuficiente");
    }
}
