package com.sa.healntrack.pharmacy_service.sales.domain;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Sale {

    private SaleId id;
    private long occurredAt;
    private SellerId sellerId;
    private BuyerId buyerId;
    private BuyerType buyerType;
    private SaleStatus status;
    private Money total;
    private List<SaleItem> items;

    public Sale(Long occurredAt,
                UUID sellerId,
                UUID buyerId,
                String buyerType,
                String status,
                BigDecimal total,
                List<SaleItem> items) {

        this.occurredAt = occurredAt != null ? occurredAt : Instant.now().toEpochMilli();
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener items");
        }
        this.sellerId = new SellerId(sellerId);
        this.buyerId = new BuyerId(buyerId);
        this.buyerType = BuyerType.safeValueOf(buyerType);
        this.status = SaleStatus.safeValueOf(status);
        this.total = new Money(total);
        this.items = new ArrayList<>(items);
    }

    public static Sale restore(
            UUID id,
            long occurredAt,
            UUID sellerId,
            UUID buyerId,
            String buyerType,
            String status,
            BigDecimal total,
            List<SaleItem> items
    ) {
        Sale s = new Sale(occurredAt, sellerId, buyerId, buyerType, status, total, items);
        s.id = new SaleId(id);
        return s;
    }

    public void complete() {
        if (this.status == SaleStatus.COMPLETED) {
            throw new IllegalStateException("La venta ya está completada");
        }
        this.status = SaleStatus.COMPLETED;
    }

}
