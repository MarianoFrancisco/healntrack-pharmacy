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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SaleController.class)
@DisplayName("SaleController")
class SaleControllerTest {

    private static final UUID SELLER_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID BUYER_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final UUID SALE_ID = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");
    private static final UUID ITEM_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
    private static final String BUYER_TYPE = "PATIENT";
    private static final String MED_CODE = "PARA-500";
    private static final BigDecimal PRICE = new BigDecimal("10.00");
    private static final BigDecimal COST = new BigDecimal("5.00");
    private static final long FIXED_TS = Instant.parse("2025-01-01T00:00:00Z").toEpochMilli();

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
    @DisplayName("POST /api/v1/sales crea una venta")
    void create_ok() throws Exception {
        CreateSaleDTO dto = new CreateSaleDTO(
                SELLER_ID, BUYER_ID, BUYER_TYPE,
                List.of(new CreateSaleItemDTO(MED_CODE, 2))
        );

        SaleItem item = new SaleItem(ITEM_ID, MED_CODE, 2, PRICE, COST);
        Sale saved = Sale.restore(
                SALE_ID,
                FIXED_TS,
                SELLER_ID,
                BUYER_ID,
                BUYER_TYPE,
                SaleStatus.COMPLETED.name(),
                new BigDecimal("20.00"),
                List.of(item)
        );

        when(createSale.handle(any(CreateSaleCommand.class))).thenReturn(saved);

        mvc.perform(post("/api/v1/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(SALE_ID.toString()))
                .andExpect(jsonPath("$.buyerType").value(BUYER_TYPE))
                .andExpect(jsonPath("$.total").value(20.0));

        ArgumentCaptor<CreateSaleCommand> captor = ArgumentCaptor.forClass(CreateSaleCommand.class);
        verify(createSale).handle(captor.capture());
        assertThat(captor.getValue().buyerId()).isEqualTo(BUYER_ID);
        assertThat(captor.getValue().sellerId()).isEqualTo(SELLER_ID);
    }

    @Test
    @DisplayName("GET /api/v1/sales/{id} devuelve una venta")
    void get_ok() throws Exception {
        SaleItem item = new SaleItem(ITEM_ID, MED_CODE, 1, PRICE, COST);

        Sale sale = Sale.restore(
                SALE_ID,
                FIXED_TS,
                SELLER_ID,
                BUYER_ID,
                BUYER_TYPE,
                SaleStatus.COMPLETED.name(),
                new BigDecimal("10.00"),
                List.of(item)
        );

        when(getSaleById.handle(any(GetSaleByIdQuery.class))).thenReturn(sale);

        mvc.perform(get("/api/v1/sales/{id}", SALE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(SALE_ID.toString()))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.items[0].medicineCode").value(MED_CODE))
                .andExpect(jsonPath("$.items[0].unitPrice").value(10.00))
                .andExpect(jsonPath("$.items[0].unitCost").value(5.00));
    }
}
