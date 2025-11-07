package com.sa.healntrack.pharmacy_service.sales.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SaleItemTest {

    private static final UUID ITEM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID MED_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final String CODE = "PARA-500";

    @Test
    void constructor_sets_lineTotal_as_unitPrice_times_quantity_and_keeps_unitCost() {
        SaleItem si = new SaleItem(MED_ID, CODE, 3, new BigDecimal("10.50"), new BigDecimal("7.25"));

        assertThat(si.getQuantity()).isEqualTo(3);
        assertThat(si.getUnitPrice().value()).isEqualByComparingTo("10.50");
        assertThat(si.getUnitCost().value()).isEqualByComparingTo("7.25");
        assertThat(si.getLineTotal().value()).isEqualByComparingTo("31.50");
        assertThat(si.getMedicineCode().value()).isEqualTo(CODE);
        assertThat(si.getMedicineId().value()).isEqualTo(MED_ID);
    }

    @Test
    void restore_keeps_given_lineTotal() {
        SaleItem si = SaleItem.restore(
                ITEM_ID, MED_ID, "IBU-200", 2,
                new BigDecimal("4.00"), new BigDecimal("2.10"),
                new BigDecimal("8.00")
        );
        assertThat(si.getId().value()).isEqualTo(ITEM_ID);
        assertThat(si.getLineTotal().value()).isEqualByComparingTo("8.00");
    }

    @Test
    void quantity_cannot_be_negative() {
        assertThatThrownBy(() -> new SaleItem(MED_ID, "XXXX-1234", -1, BigDecimal.ONE, BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mayor o igual a cero");
    }
}
