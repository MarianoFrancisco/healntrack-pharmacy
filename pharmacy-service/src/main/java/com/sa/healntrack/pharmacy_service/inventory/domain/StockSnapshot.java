package com.sa.healntrack.pharmacy_service.inventory.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineId;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.Quantity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class StockSnapshot {
    private MedicineId medicineId;
    private Quantity totalQuantity;
    private long updatedAt;

    public StockSnapshot(UUID medicineId, int totalQuantity) {
        this.medicineId = new MedicineId(medicineId);
        this.totalQuantity = new Quantity(Math.max(0, totalQuantity));
        this.updatedAt = Instant.now().toEpochMilli();
    }

    public static StockSnapshot restore(UUID medicineId, int totalQuantity, long updatedAt) {
        StockSnapshot s = new StockSnapshot(medicineId, totalQuantity);
        s.updatedAt = updatedAt;
        return s;
    }

    public void applyDelta(int delta) {
        int next = this.totalQuantity.value() + delta;
        if (next < 0) throw new IllegalStateException("El stock quedaría negativo");
        this.totalQuantity = new Quantity(next);
        this.updatedAt = Instant.now().toEpochMilli();
    }
}
