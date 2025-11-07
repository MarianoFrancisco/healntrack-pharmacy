package com.sa.healntrack.pharmacy_service.sales.domain;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SaleTest {

    private static final UUID SELLER = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID BUYER = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final UUID SALE_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
    private static final UUID ITEM_ID = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");

    @Test
    void must_have_items() {
        assertThatThrownBy(() -> new Sale(
                System.currentTimeMillis(),
                SELLER, BUYER,
                "PATIENT", "PENDING", BigDecimal.TEN, List.of()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("debe tener items");
    }

    @Test
    void complete_changes_status_to_completed_and_prevents_duplicate_complete() {
        SaleItem item = new SaleItem(ITEM_ID, "XXXX-1123", 1, new BigDecimal("5.00"), new BigDecimal("2.00"));
        Sale s = new Sale(System.currentTimeMillis(), SELLER, BUYER,
                "PATIENT", "OPEN", new BigDecimal("5.00"), List.of(item));

        s.complete();
        assertThat(s.getStatus()).isEqualTo(SaleStatus.COMPLETED);

        assertThatThrownBy(s::complete)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ya está completada");
    }

    @Test
    void restore_sets_id_and_keeps_values() {
        SaleItem item = new SaleItem(ITEM_ID, "XXXX-1234", 2, new BigDecimal("3.00"), new BigDecimal("1.00"));
        Sale restored = Sale.restore(SALE_ID, System.currentTimeMillis(), SELLER, BUYER,
                "PATIENT", "COMPLETED", new BigDecimal("6.00"), List.of(item));
        assertThat(restored.getId().value()).isEqualTo(SALE_ID);
        assertThat(restored.getItems()).hasSize(1);
    }
}
