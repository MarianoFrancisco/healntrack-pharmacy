package com.sa.healntrack.pharmacy_service.inventory.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineId;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Batch {
    private BatchId id;
    private MedicineId medicineId;
    private LocalDate expirationDate;
    private Quantity purchasedQuantity;
    private Quantity quantityOnHand;
    private PositiveMoney purchasePrice;
    private PurchasedById purchasedBy;
    private long createdAt;
    private long updatedAt;
    @Setter
    private BatchMedicine medicine;
    @Setter
    private BatchEmployee employee;

    public Batch(UUID medicineId, LocalDate expirationDate,
                 Integer purchasedQuantity, BigDecimal purchasePrice, UUID purchasedBy) {

        this.medicineId = new MedicineId(medicineId);
        this.expirationDate = expirationDate;
        this.purchasedQuantity = Quantity.ofPositive(purchasedQuantity != null ? purchasedQuantity : 0);
        this.quantityOnHand = new Quantity(this.purchasedQuantity.value());
        this.purchasePrice = new PositiveMoney(purchasePrice);
        this.purchasedBy = new PurchasedById(purchasedBy);
        long now = Instant.now().toEpochMilli();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static Batch restore(UUID id, UUID medicineId, LocalDate expirationDate,
                                int purchasedQuantity, int quantityOnHand, BigDecimal purchasePrice,
                                UUID purchasedBy, long createdAt, long updatedAt) {
        Batch b = new Batch(medicineId, expirationDate, purchasedQuantity, purchasePrice, purchasedBy);
        b.id = new BatchId(id);
        if (quantityOnHand < 0 || quantityOnHand > purchasedQuantity) {
            throw new IllegalArgumentException("Cantidad en mano inválida");
        }
        b.quantityOnHand = new Quantity(quantityOnHand);
        b.createdAt = createdAt;
        b.updatedAt = updatedAt;
        return b;
    }

    public boolean isExpired(LocalDate today) {
        return expirationDate != null && expirationDate.isBefore(today.plusDays(1));
    }

    public int consume(int qty) {
        Quantity toConsume = Quantity.ofPositive(qty);
        if (toConsume.value() > this.quantityOnHand.value()) {
            throw new IllegalStateException("Stock insuficiente en el lote");
        }
        this.quantityOnHand = this.quantityOnHand.sub(toConsume.value());
        this.updatedAt = Instant.now().toEpochMilli();
        return toConsume.value();
    }
}
