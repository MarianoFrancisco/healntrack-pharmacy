package com.sa.healntrack.pharmacy_service.inventory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Batch")
class BatchTest {

    @Test
    @DisplayName("constructor setea cantidades y timestamps")
    void constructor_sets_quantities_and_timestamps() {
        UUID med = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        UUID buyer = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

        Batch b = new Batch(med, LocalDate.now().plusMonths(6), 10, new BigDecimal("2.50"), buyer);

        assertThat(b.getPurchasedQuantity().value()).isEqualTo(10);
        assertThat(b.getQuantityOnHand().value()).isEqualTo(10);
        assertThat(b.getPurchasePrice().value()).isEqualByComparingTo("2.50");
        assertThat(b.getCreatedAt()).isPositive();
        assertThat(b.getUpdatedAt()).isPositive();
    }

    @Test
    @DisplayName("restore valida quantityOnHand no negativa ni mayor a purchased")
    void restore_validates_quantity_on_hand_not_negative_nor_greater_than_purchased() {
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        UUID med = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
        UUID buyer = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");

        Batch ok = Batch.restore(id, med, LocalDate.now(), 10, 5, new BigDecimal("3.00"),
                buyer, 100L, 200L);
        assertThat(ok.getQuantityOnHand().value()).isEqualTo(5);

        assertThatThrownBy(() -> Batch.restore(id, med, LocalDate.now(), 10, -1, new BigDecimal("3.00"),
                buyer, 100L, 200L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cantidad en mano inválida");

        assertThatThrownBy(() -> Batch.restore(id, med, LocalDate.now(), 10, 11, new BigDecimal("3.00"),
                buyer, 100L, 200L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cantidad en mano inválida");
    }

    @Test
    @DisplayName("consume disminuye stock, actualiza timestamp y evita sobre consumo")
    void consume_decreases_stock_and_updates_timestamp_and_prevents_over_consume() {
        Batch b = new Batch(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                LocalDate.now().plusDays(1),
                5, new BigDecimal("1.00"), UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));

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
