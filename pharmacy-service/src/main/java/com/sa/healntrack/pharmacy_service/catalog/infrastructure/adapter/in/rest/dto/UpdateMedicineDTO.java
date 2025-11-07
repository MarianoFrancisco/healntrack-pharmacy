package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateMedicineDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 120, message = "El nombre no puede tener más de 120 caracteres")
        String name,

        @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
        String description,

        @NotBlank(message = "El tipo de unidad es obligatorio")
        String unitType,

        @NotNull(message = "El stock mínimo es obligatorio")
        @Min(value = 0, message = "El stock mínimo no puede ser menor a 0")
        Integer minStock,

        @NotNull(message = "El precio actual es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio actual no puede ser menor a 0")
        BigDecimal currentPrice,

        @NotNull(message = "El costo actual es obligatorio")
        @DecimalMin(value = "0.0", message = "El costo actual no puede ser menor a 0")
        BigDecimal currentCost
) {
}
