package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_snapshot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockSnapshotEntity {
    @Id
    @Column(name = "medicine_id")
    private UUID medicineId;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
