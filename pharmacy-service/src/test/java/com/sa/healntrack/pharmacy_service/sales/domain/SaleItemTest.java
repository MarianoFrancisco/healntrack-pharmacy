package com.sa.healntrack.pharmacy_service.sales.domain;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SaleItem")
class SaleItemTest {

    @Test
    @DisplayName("constructor calcula lineTotal y conserva unitCost")
    void constructor_sets_lineTotal_as_unitPrice_times_quantity_and_keeps_unitCost() {
        UUID medId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        SaleItem si = new SaleItem(medId, "PARA-500", 3, new BigDecimal("10.50"), new BigDecimal("7.25"));

        assertThat(si.getQuantity()).isEqualTo(3);
        assertThat(si.getUnitPrice()).isEqualTo(new Money(new BigDecimal("10.50")));
        assertThat(si.getUnitCost()).isEqualTo(new Money(new BigDecimal("7.25")));
        assertThat(si.getLineTotal()).isEqualTo(new Money(new BigDecimal("31.50")));
        assertThat(si.getMedicineCode().value()).isEqualTo("PARA-500");
        assertThat(si.getMedicineId().value()).isEqualTo(medId);
    }

    @Test
    @DisplayName("restore conserva lineTotal dado")
    void restore_keeps_given_lineTotal() {
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        UUID medId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        SaleItem si = SaleItem.restore(
                id, medId, "IBU-200", 2,
                new BigDecimal("4.00"), new BigDecimal("2.10"),
                new BigDecimal("8.00")
        );
        assertThat(si.getId().value()).isEqualTo(id);
        assertThat(si.getLineTotal()).isEqualTo(new Money(new BigDecimal("8.00")));
    }

    @Test
    @DisplayName("quantity no puede ser negativa")
    void quantity_cannot_be_negative() {
        UUID medId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThatThrownBy(() -> new SaleItem(medId, "XXXX-1234", -1, BigDecimal.ONE, BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mayor o igual a cero");
    }
}
