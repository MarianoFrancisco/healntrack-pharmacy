package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "monthly_closings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"year", "month"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyClosingEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(name = "closed_by")
    private UUID closedBy;

    @Column(name = "closed_at", nullable = false)
    private OffsetDateTime closedAt;
}
