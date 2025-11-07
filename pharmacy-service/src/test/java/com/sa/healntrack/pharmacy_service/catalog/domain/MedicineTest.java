package com.sa.healntrack.pharmacy_service.catalog.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Medicine")
class MedicineTest {

    private static final UUID FIXED_ID = UUID.fromString("11111111-2222-3333-4444-555555555555");
    private static final String CODE = "PARA-500";
    private static final String NAME = "Paracetamol 500mg";
    private static final String DESC = "Analgésico";

    private Medicine newMed;

    @BeforeEach
    void setUp() {
        newMed = new Medicine(CODE, NAME, DESC, "UNIT", 0, new BigDecimal("10.00"), new BigDecimal("6.00"));
    }

    @Test
    @DisplayName("constructor valida y setea defaults")
    void constructor_validates_fields_and_sets_defaults() {
        assertThat(newMed.getStatus()).isEqualTo(MedicineStatus.ACTIVE);
        assertThat(newMed.getCurrentPrice()).isEqualByComparingTo("10.00");
        assertThat(newMed.getCurrentCost()).isEqualByComparingTo("6.00");
        assertThat(newMed.getCode().value()).isEqualTo(CODE);
        assertThat(newMed.getName()).isEqualTo(NAME);
        assertThat(newMed.getDescription()).isEqualTo(DESC);
    }

    @Test
    @DisplayName("restore setea id y status dados")
    void restore_sets_id_and_status() {
        var restored = Medicine.restore(FIXED_ID, "CCCC-1234", "N", "D", "INACTIVE", "UNIT", 0,
                new BigDecimal("1.00"), new BigDecimal("0.50"), 1L, 2L);
        assertThat(restored.getStatus()).isEqualTo(MedicineStatus.INACTIVE);
        assertThat(restored.getCreatedAt()).isEqualTo(1L);
        assertThat(restored.getUpdatedAt()).isEqualTo(2L);
        assertThat(restored.getId().value()).isEqualTo(FIXED_ID);
        assertThat(restored.getCode().value()).isEqualTo("CCCC-1234");
    }

    @Test
    @DisplayName("activar/desactivar respeta transiciones")
    void activate_deactivate_transitions_with_checks() {
        Medicine m = new Medicine("XXXX-12", "N", "D", "UNIT", 0, BigDecimal.ONE, BigDecimal.ONE);
        m.deactivate();
        assertThat(m.getStatus()).isEqualTo(MedicineStatus.INACTIVE);
        assertThatThrownBy(m::deactivate).isInstanceOf(IllegalStateException.class);
        m.activate();
        assertThat(m.getStatus()).isEqualTo(MedicineStatus.ACTIVE);
        assertThatThrownBy(m::activate).isInstanceOf(IllegalStateException.class);
    }
}
