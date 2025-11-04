package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.controller;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.activate_medicine.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.create_medicine.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.deactivate_medicine.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicine_by_id.*;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.update_medicine.*;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.dto.*;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.mapper.MedicineRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final CreateMedicine createMedicine;
    private final UpdateMedicine updateMedicine;
    private final ActivateMedicine activateMedicine;
    private final DeactivateMedicine deactivateMedicine;
    private final GetMedicineByCode getByCode;
    private final GetAllMedicines getAll;

    @PostMapping
    public ResponseEntity<MedicineResponseDTO> create(@Valid @RequestBody CreateMedicineDTO dto) {
        Medicine saved = createMedicine.handle(MedicineRestMapper.toCommand(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(MedicineRestMapper.toResponse(saved));
    }

    @PatchMapping("/{code}")
    public MedicineResponseDTO update(@PathVariable String code, @Valid @RequestBody UpdateMedicineDTO dto) {
        Medicine updated = updateMedicine.handle(code, MedicineRestMapper.toCommand(dto));
        return MedicineRestMapper.toResponse(updated);
    }

    @PostMapping("/{code}:activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable String code) {
        activateMedicine.handle(new ActivateMedicineCommand(code));
    }

    @PostMapping("/{code}:deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable String code) {
        deactivateMedicine.handle(new DeactivateMedicineCommand(code));
    }

    @GetMapping("/{code}")
    public MedicineResponseDTO getByCode(@PathVariable String code) {
        Medicine m = getByCode.handle(new GetMedicineByCodeQuery(code));
        return MedicineRestMapper.toResponse(m);
    }

    @GetMapping
    public List<MedicineResponseDTO> getAll(@RequestParam(required = false) String searchTerm,
                                            @RequestParam(required = false) Boolean isActive) {
        return getAll.handle(new GetAllMedicinesQuery(searchTerm, isActive))
                .stream().map(MedicineRestMapper::toResponse).toList();
    }
}
