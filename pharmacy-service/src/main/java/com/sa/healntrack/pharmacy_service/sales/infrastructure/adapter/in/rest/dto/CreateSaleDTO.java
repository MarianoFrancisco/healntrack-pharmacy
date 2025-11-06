package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateSaleDTO(

        @NotNull(message = "El identificador del vendedor es obligatorio")
        UUID sellerId,

        @NotNull(message = "El identificador del comprador es obligatorio")
        UUID buyerId,

        @NotBlank(message = "El tipo de comprador es obligatorio")
        String buyerType,

        @NotEmpty(message = "La lista de items no puede estar vacía")
        List<CreateSaleItemDTO> items

) {
}
