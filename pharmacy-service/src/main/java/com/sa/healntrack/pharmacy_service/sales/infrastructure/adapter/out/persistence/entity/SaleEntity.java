package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.entity;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.BuyerType;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleEntity {
    @Id
    private UUID id;

    @Column(name = "occurred_at", nullable = false)
    private OffsetDateTime occurredAt;

    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;

    @Column(name = "buyer_id", nullable = false)
    private UUID buyerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private BuyerType buyerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private SaleStatus status;

    @Column(name = "total", precision = 14, scale = 2, nullable = false)
    private BigDecimal grandTotal;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SaleItemEntity> items = new ArrayList<>();
}
