package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.controller;

import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_snapshot.GetSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.update_stock.UpdateStock;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.SnapshotResponseDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.dto.UpdateStockDTO;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.in.rest.mapper.StockSnapshotRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory/stock")
public class StockSnapshotController {

    private final GetSnapshot getSnapshot;

    @GetMapping("/{medicineCode}")
    public SnapshotResponseDTO get(@PathVariable String medicineCode) {
        return StockSnapshotRestMapper.toResponse(
                getSnapshot.handle(StockSnapshotRestMapper.toQuery(medicineCode))
        );
    }
}
