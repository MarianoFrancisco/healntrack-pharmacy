package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.controller;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.create_batch.CreateBatch;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_all_batches.GetAllBatches;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.BatchResponseDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.CreateBatchDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.mapper.BatchRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory/batches")
public class BatchController {

    private final CreateBatch createBatch;
    private final GetAllBatches getAllBatches;

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@Valid @RequestBody CreateBatchDTO dto) {
        createBatch.handle(BatchRestMapper.toCommand(dto));
    }

    @GetMapping
    public List<BatchResponseDTO> list(
            @RequestParam(required = false) String medicineCode,
            @RequestParam(required = false) Boolean onlyWithStock,
            @RequestParam(required = false) Boolean onlyNotExpired
    ) {
        List<Batch> result = getAllBatches.handle(
                BatchRestMapper.toQuery(medicineCode, onlyWithStock, onlyNotExpired)
        );
        return BatchRestMapper.toResponse(result);
    }
}
