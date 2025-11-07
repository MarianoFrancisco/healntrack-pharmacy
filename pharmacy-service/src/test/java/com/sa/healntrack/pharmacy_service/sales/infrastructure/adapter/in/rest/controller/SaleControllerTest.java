package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id.GetSaleById;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_sale_by_id.GetSaleByIdQuery;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.CreateSaleDTO;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto.CreateSaleItemDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SaleController.class)
@DisplayName("SaleController")
class SaleControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockitoBean
    CreateSale createSale;
    @MockitoBean
    GetSaleById getSaleById;
    @MockitoBean
    GetAllSales getAllSales;

    @Test
    @DisplayName("POST /api/v1/sales — crea venta y regresa 201 con payload")
    void create_ok() throws Exception {
        UUID seller = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        UUID buyer = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

        CreateSaleDTO dto = new CreateSaleDTO(
                seller, buyer, "PATIENT",
                List.of(new CreateSaleItemDTO("PARA-500", 2))
        );

        SaleItem item = new SaleItem(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"),
                "PARA-500", 2, new BigDecimal("10.00"), new BigDecimal("5.00"));

        Sale saved = Sale.restore(
                UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"),
                Instant.parse("2025-01-01T00:00:00Z").toEpochMilli(),
                seller, buyer, "PATIENT",
                SaleStatus.COMPLETED.name(),
                new BigDecimal("20.00"),
                List.of(item)
        );

        when(createSale.handle(any(CreateSaleCommand.class))).thenReturn(saved);

        mvc.perform(post("/api/v1/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.buyerType").value("PATIENT"))
                .andExpect(jsonPath("$.total").isNumber())
                .andExpect(jsonPath("$.total").value(20.0));

        ArgumentCaptor<CreateSaleCommand> captor = ArgumentCaptor.forClass(CreateSaleCommand.class);
        verify(createSale).handle(captor.capture());
        assertThat(captor.getValue().buyerType()).isEqualTo("PATIENT");
        assertThat(captor.getValue().items()).hasSize(1);
    }

    @Test
    @DisplayName("GET /api/v1/sales/{id} — devuelve una venta")
    void get_ok() throws Exception {
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        UUID seller = UUID.fromString("66666666-7777-8888-9999-000000000000");
        UUID buyer = UUID.fromString("12121212-3434-5656-7878-909090909090");

        Sale s = Sale.restore(
                id,
                Instant.parse("2025-01-02T00:00:00Z").toEpochMilli(),
                seller,
                buyer,
                "PATIENT",
                SaleStatus.COMPLETED.name(),
                new BigDecimal("10.00"),
                List.of(new SaleItem(
                        UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"),
                        "PARA-500",
                        1,
                        new BigDecimal("10.00"),
                        new BigDecimal("3.00")
                ))
        );

        when(getSaleById.handle(any(GetSaleByIdQuery.class))).thenReturn(s);

        mvc.perform(get("/api/v1/sales/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.items[0].medicineCode").value("PARA-500"))
                .andExpect(jsonPath("$.items[0].quantity").value(1))
                .andExpect(jsonPath("$.items[0].unitPrice").isNumber())
                .andExpect(jsonPath("$.items[0].unitCost").isNumber());
    }
}
