package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.out.persistence.entity;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineStatus;
import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.UnitType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@DynamicInsert
@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "medicine_status")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private MedicineStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false, columnDefinition = "unit_type")
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UnitType unitType;

    @Column(name = "min_stock", nullable = false)
    private Integer minStock;

    @Column(name = "current_price", precision = 14, scale = 2, nullable = false)
    private BigDecimal currentPrice;

    @Column(name = "current_cost", precision = 14, scale = 2, nullable = false)
    private BigDecimal currentCost;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (status == null) {
            status = MedicineStatus.ACTIVE;
        }
        if (createdAt == null) {
            createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }


    @PreUpdate
    void preUpdate() {
        if (updatedAt == null) {
            updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
        }
    }
}
