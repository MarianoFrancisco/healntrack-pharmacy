package com.sa.healntrack.pharmacy_service.catalog.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MedicineTest {

    private static final String CODE = "PARA-500";
    private static final String NAME = "Paracetamol 500mg";
    private static final String DESC = "Analgésico";
    private static final String UNIT = "UNIT";
    private static final int STOCK = 0;
    private static final BigDecimal PRICE = new BigDecimal("10.00");
    private static final BigDecimal COST = new BigDecimal("6.00");
    private static final UUID ID = UUID.fromString("11111111-2222-3333-4444-555555555555");

    @Test
    void constructor_validates_fields_and_sets_defaults() {
        Medicine m = new Medicine(CODE, NAME, DESC, UNIT, STOCK, PRICE, COST);
        assertThat(m.getStatus()).isEqualTo(MedicineStatus.ACTIVE);
        assertThat(m.getCurrentPrice()).isEqualByComparingTo(PRICE);
        assertThat(m.getCurrentCost()).isEqualByComparingTo(COST);
        assertThat(m.getCode().value()).isEqualTo(CODE);
    }

    @Test
    void activate_deactivate_transitions_with_checks() {
        Medicine m = new Medicine("XXXX-12", "N", "D", "UNIT", 0, BigDecimal.ONE, BigDecimal.ONE);
        m.deactivate();
        assertThat(m.getStatus()).isEqualTo(MedicineStatus.INACTIVE);
        assertThatThrownBy(m::deactivate).isInstanceOf(IllegalStateException.class);

        m.activate();
        assertThat(m.getStatus()).isEqualTo(MedicineStatus.ACTIVE);
        assertThatThrownBy(m::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void restore_sets_id_and_status() {
        Medicine restored = Medicine.restore(
                ID, "CCCC-1234", "N", "D", "INACTIVE", "UNIT", 0, BigDecimal.ONE, BigDecimal.ONE, 1L, 2L
        );
        assertThat(restored.getStatus()).isEqualTo(MedicineStatus.INACTIVE);
        assertThat(restored.getCreatedAt()).isEqualTo(1L);
        assertThat(restored.getUpdatedAt()).isEqualTo(2L);
        assertThat(restored.getId().value()).isEqualTo(ID);
    }
}
