package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.mapper;

import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.Item;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.List;

@UtilityClass
public class SaleRestMapper {

    public CreateSaleCommand toCommand(CreateSaleDTO d) {
        List<Item> items = d.items().stream()
                .map(it -> new Item(
                        it.medicineCode(),
                        it.quantity()
                ))
                .toList();

        return new CreateSaleCommand(
                Instant.now().toEpochMilli(),
                d.sellerId(),
                d.buyerId(),
                d.buyerType(),
                items
        );
    }

    public SaleResponseDTO toResponse(Sale s) {
        List<SaleItemResponseDTO> lines = s.getItems().stream()
                .map(SaleRestMapper::toResponse)
                .toList();

        return new SaleResponseDTO(
                s.getId() != null ? s.getId().value() : null,
                s.getOccurredAt(),
                s.getSellerId().value(),
                s.getBuyerId().value(),
                s.getBuyerType().name(),
                s.getStatus(),
                s.getTotal().value(),
                lines,
                s.getSeller(),
                s.getBuyer()
        );
    }

    private SaleItemResponseDTO toResponse(SaleItem it) {
        return new SaleItemResponseDTO(
                it.getId() != null ? it.getId().value() : null,
                it.getMedicineId().value(),
                it.getMedicineCode().value(),
                it.getQuantity(),
                it.getUnitPrice().value(),
                it.getUnitCost() != null ? it.getUnitCost().value() : null,
                it.getLineTotal().value(),
                it.getMedicine()
        );
    }
}

