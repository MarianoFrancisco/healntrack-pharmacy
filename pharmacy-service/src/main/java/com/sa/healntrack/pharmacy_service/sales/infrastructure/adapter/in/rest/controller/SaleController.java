package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.controller;

import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.complete_sale.CompleteSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.complete_sale.CompleteSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSalesQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id.GetSaleById;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id.GetSaleByIdQuery;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.CreateSaleDTO;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.SaleResponseDTO;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.mapper.SaleRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final CreateSale createSale;
    private final GetSaleById getSaleById;
    private final GetAllSales getAllSales;
    private final CompleteSale completeSale;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponseDTO create(@Valid @RequestBody CreateSaleDTO dto) {
        Sale saved = createSale.handle(SaleRestMapper.toCommand(dto));
        return SaleRestMapper.toResponse(saved);
    }

    @PostMapping("/{id}:complete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void complete(@PathVariable UUID id) {
        completeSale.handle(new CompleteSaleCommand(id));
    }

    @GetMapping("/{id}")
    public SaleResponseDTO get(@PathVariable UUID id) {
        Sale s = getSaleById.handle(new GetSaleByIdQuery(id));
        return SaleRestMapper.toResponse(s);
    }

    @GetMapping
    public List<SaleResponseDTO> list(@RequestParam(required = false) Long occurredFrom,
                                      @RequestParam(required = false) Long occurredTo,
                                      @RequestParam(required = false) UUID sellerId,
                                      @RequestParam(required = false) String status) {
        SaleStatus st = status != null && !status.isBlank() ? SaleStatus.safeValueOf(status) : null;
        return getAllSales.handle(new GetAllSalesQuery(occurredFrom, occurredTo, sellerId, st))
                .stream().map(SaleRestMapper::toResponse).collect(Collectors.toList());
    }
}
