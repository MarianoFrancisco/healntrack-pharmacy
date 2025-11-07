package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "monthly_closing_lines",
        uniqueConstraints = @UniqueConstraint(columnNames = {"closing_id", "medicine_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyClosingLineEntity {
    @Id
    private UUID id;

    @Column(name = "closing_id", nullable = false)
    private UUID closingId;

    @Column(name = "medicine_id", nullable = false)
    private UUID medicineId;

    @Column(name = "system_qty", nullable = false)
    private Integer systemQty;

    @Column(name = "physical_qty", nullable = false)
    private Integer physicalQty;

    @Column(name = "variance", nullable = false)
    private Integer variance;

    @Column(name = "unit_cost", precision = 14, scale = 2, nullable = false)
    private BigDecimal unitCost;

    @Column(name = "variance_value", precision = 14, scale = 2, nullable = false)
    private BigDecimal varianceValue;

    @Column(name = "note", length = 500)
    private String note;
}
