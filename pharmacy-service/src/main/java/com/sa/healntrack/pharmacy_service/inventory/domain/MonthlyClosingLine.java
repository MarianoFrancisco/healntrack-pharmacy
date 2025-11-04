package com.sa.healntrack.pharmacy_service.inventory.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineId;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.MonthlyClosingId;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.MonthlyClosingLineId;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Getter
public class MonthlyClosingLine {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    private MonthlyClosingLineId id;
    private MonthlyClosingId closingId;
    private MedicineId medicineId;
    private int systemQty;
    private int physicalQty;
    private int variance;
    private BigDecimal unitCost;
    private BigDecimal varianceValue;
    private String note;

    public MonthlyClosingLine(UUID closingId,
                              UUID medicineId,
                              int systemQty,
                              int physicalQty,
                              BigDecimal unitCost,
                              String note
    ) {
        validateQuantities(systemQty, physicalQty);
        this.closingId = new MonthlyClosingId(closingId);
        this.medicineId = new MedicineId(medicineId);
        this.systemQty = systemQty;
        this.physicalQty = physicalQty;
        this.variance = calculateVariance(systemQty, physicalQty);
        this.unitCost = normalizeUnitCost(unitCost);
        this.varianceValue = calculateVarianceValue(this.unitCost, this.variance);
        this.note = normalizeNote(note);
    }

    public static MonthlyClosingLine restore(UUID id,
                                             UUID closingId,
                                             UUID medicineId,
                                             int systemQty,
                                             int physicalQty,
                                             int variance,
                                             BigDecimal unitCost,
                                             BigDecimal varianceValue,
                                             String note
    ) {
        MonthlyClosingLine line = new MonthlyClosingLine(closingId, medicineId, systemQty, physicalQty, unitCost, note);
        line.id = new MonthlyClosingLineId(id);
        validateRestoredData(line, variance, varianceValue);
        return line;
    }

    private static void validateQuantities(int systemQty, int physicalQty) {
        if (systemQty < 0) {
            throw new IllegalArgumentException("Cantidad en sistema no puede ser negativa");
        }
        if (physicalQty < 0) {
            throw new IllegalArgumentException("Cantidad física no puede ser negativa");
        }
    }

    private static int calculateVariance(int systemQty, int physicalQty) {
        return physicalQty - systemQty;
    }

    private static BigDecimal normalizeUnitCost(BigDecimal unitCost) {
        BigDecimal value = (unitCost == null) ? BigDecimal.ZERO : unitCost;
        if (value.signum() < 0) {
            throw new IllegalArgumentException("Costo unitario no puede ser negativo");
        }
        return value.setScale(SCALE, ROUNDING);
    }

    private static BigDecimal calculateVarianceValue(BigDecimal unitCost, int variance) {
        return unitCost
                .multiply(BigDecimal.valueOf(variance))
                .setScale(SCALE, ROUNDING);
    }

    private static String normalizeNote(String note) {
        return (note == null || note.isBlank()) ? null : note.trim();
    }

    private static void validateRestoredData(MonthlyClosingLine line, int variance, BigDecimal varianceValue) {
        if (variance != line.variance) {
            throw new IllegalArgumentException("Varianza inconsistente");
        }
        BigDecimal expected = line.varianceValue.setScale(SCALE, ROUNDING);
        BigDecimal provided = (varianceValue == null) ? null : varianceValue.setScale(SCALE, ROUNDING);
        if (provided == null || expected.compareTo(provided) != 0) {
            throw new IllegalArgumentException("Valor de varianza inconsistente");
        }
    }
}
