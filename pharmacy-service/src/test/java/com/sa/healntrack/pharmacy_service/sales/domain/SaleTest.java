package com.sa.healntrack.pharmacy_service.sales.domain;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Sale")
class SaleTest {

    @Test
    @DisplayName("debe tener items")
    void must_have_items() {
        assertThatThrownBy(() -> new Sale(
                1L,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                "PATIENT", "PENDING", BigDecimal.TEN, List.of()
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("debe tener items");
    }

    @Test
    @DisplayName("complete cambia a COMPLETED y evita duplicado")
    void complete_changes_status_to_completed_and_prevents_duplicate_complete() {
        SaleItem item = new SaleItem(
                UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"),
                "XXXX-1123", 1, new BigDecimal("5.00"), new BigDecimal("2.00")
        );
        Sale s = new Sale(1L,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                "PATIENT", "OPEN", new BigDecimal("5.00"), List.of(item));

        s.complete();
        assertThat(s.getStatus()).isEqualTo(SaleStatus.COMPLETED);

        assertThatThrownBy(s::complete)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ya está completada");
    }

    @Test
    @DisplayName("restore setea id y conserva valores")
    void restore_sets_id_and_keeps_values() {
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        SaleItem item = new SaleItem(
                UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"),
                "XXXX-1234", 2, new BigDecimal("3.00"), new BigDecimal("1.00")
        );
        var restored = Sale.restore(id, 2L,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                "PATIENT", "COMPLETED", new BigDecimal("6.00"), List.of(item));
        assertThat(restored.getId().value()).isEqualTo(id);
        assertThat(restored.getItems()).hasSize(1);
    }
}
