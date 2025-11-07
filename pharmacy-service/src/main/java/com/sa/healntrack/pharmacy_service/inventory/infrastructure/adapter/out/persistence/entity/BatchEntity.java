package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchEntity {
    @Id
    private UUID id;

    @Column(name = "medicine_id", nullable = false)
    private UUID medicineId;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "purchased_quantity", nullable = false)
    private Integer purchasedQuantity;

    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand;

    @Column(name = "purchase_price", precision = 14, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "purchased_by")
    private UUID purchasedBy;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
