package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.mapper;

import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleId;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleItemId;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.entity.SaleEntity;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.persistence.entity.SaleItemEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class SaleEntityMapper {

    public Sale toDomain(SaleEntity e) {
        long occurred = e.getOccurredAt().toInstant().toEpochMilli();
        List<SaleItem> items = new ArrayList<>();
        for (SaleItemEntity i : e.getItems()) {
            SaleItem si = SaleItem.restore(
                    i.getId(),
                    i.getMedicineId(),
                    i.getMedicineCode(),
                    i.getQuantity(),
                    i.getUnitPrice(),
                    i.getUnitCost(),
                    i.getLineTotal()
            );
            items.add(si);
        }
        return Sale.restore(
                e.getId(),
                occurred,
                e.getSellerId(),
                e.getStatus().name(),
                e.getGrandTotal(),
                items
        );
    }

    public SaleEntity toEntity(Sale d) {
        OffsetDateTime occurred = OffsetDateTime.ofInstant(Instant.ofEpochMilli(d.getOccurredAt()), ZoneOffset.UTC);
        SaleEntity e = SaleEntity.builder()
                .id(d.getId() != null ? d.getId().value() : UUID.randomUUID())
                .occurredAt(occurred)
                .sellerId(d.getSellerId().value())
                .status(d.getStatus())
                .grandTotal(d.getTotal().value())
                .build();

        List<SaleItemEntity> outItems = new ArrayList<>();
        for (SaleItem si : d.getItems()) {
            SaleItemEntity ie = SaleItemEntity.builder()
                    .id(si.getId() != null ? si.getId().value() : UUID.randomUUID())
                    .sale(e)
                    .medicineId(si.getMedicineId().value())
                    .medicineCode(si.getMedicineCode().value())
                    .quantity(si.getQuantity())
                    .unitPrice(si.getUnitPrice().value())
                    .unitCost(si.getUnitCost().value())
                    .lineTotal(si.getLineTotal().value())
                    .build();
            outItems.add(ie);
        }
        e.setItems(outItems);
        return e;
    }
}
