package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.mapper;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_batch.CreateBatchCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_batches.GetAllBatchesQuery;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.BatchResponseDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.CreateBatchDTO;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BatchRestMapper {

    public CreateBatchCommand toCommand(CreateBatchDTO d) {
        return new CreateBatchCommand(
                d.medicineCode(),
                d.expirationDate(),
                d.purchasedQuantity(),
                d.purchasedBy()
        );
    }

    public GetAllBatchesQuery toQuery(String medicineCode, Boolean onlyWithStock, Boolean onlyNotExpired) {
        return new GetAllBatchesQuery(medicineCode, onlyWithStock, onlyNotExpired);
    }

    public BatchResponseDTO toResponse(Batch b) {
        return new BatchResponseDTO(
                b.getId() != null ? b.getId().value() : null,
                b.getMedicineId().value(),
                b.getExpirationDate(),
                b.getPurchasedQuantity().value(),
                b.getQuantityOnHand().value(),
                b.getPurchasePrice() != null ? b.getPurchasePrice().value() : null,
                b.getPurchasedBy(),
                b.getCreatedAt(),
                b.getUpdatedAt()
        );
    }

    public List<BatchResponseDTO> toResponse(List<Batch> list) {
        return list.stream().map(BatchRestMapper::toResponse).collect(Collectors.toList());
    }
}
