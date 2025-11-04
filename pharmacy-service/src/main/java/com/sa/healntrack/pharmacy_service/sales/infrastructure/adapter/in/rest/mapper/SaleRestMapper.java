package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.mapper;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Item;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaleRestMapper {

    private final FindMedicines findMedicines;

    public CreateSaleCommand toCommand(CreateSaleDTO d) {
        List<Item> items = d.items().stream().map(it -> {
            Medicine med = findMedicines.findByCode(it.medicineCode())
                    .orElseThrow(() -> new MedicineNotFoundException(it.medicineCode()));
            return new Item(
                    med.getId().value(),
                    it.medicineCode(),
                    it.quantity(),
                    med.getCurrentPrice(),
                    med.getCurrentCost()
            );
        }).collect(Collectors.toList());

        return new CreateSaleCommand(
                Instant.now().toEpochMilli(),
                d.sellerId(),
                items
        );
    }

    public SaleResponseDTO toResponse(Sale s) {
        List<SaleItemResponseDTO> lines = s.getItems().stream().map(this::toResponse).collect(Collectors.toList());
        return new SaleResponseDTO(
                s.getId() != null ? s.getId().value() : null,
                s.getOccurredAt(),
                s.getSellerId().value(),
                s.getStatus(),
                s.getTotal().value(),
                lines
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
                it.getLineTotal().value()
        );
    }
}
