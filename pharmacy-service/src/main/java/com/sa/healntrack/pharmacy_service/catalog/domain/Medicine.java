package com.sa.healntrack.pharmacy_service.catalog.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineCode;
import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineId;
import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineStatus;
import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.UnitType;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Medicine {
    private MedicineId id;
    private MedicineCode code;
    private String name;
    private String description;
    private MedicineStatus status;
    private UnitType unitType;
    private int minStock;
    private BigDecimal currentPrice;
    private BigDecimal currentCost;
    private long createdAt;
    private long updatedAt;

    public Medicine(
            String code,
            String name,
            String description,
            String unit,
            Integer minStock,
            BigDecimal currentPrice,
            BigDecimal currentCost
    ) {
        this.id = new MedicineId(UUID.randomUUID());
        this.code = new MedicineCode(code);
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.unitType = UnitType.safeValueOf(unit);
        this.minStock = validateMinStock(minStock);
        this.currentPrice = validateMoneyValue(currentPrice, "precio actual");
        this.currentCost = validateMoneyValue(currentCost, "costo actual");
        this.status = MedicineStatus.ACTIVE;
        long now = Instant.now().toEpochMilli();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static Medicine restore(
            UUID id,
            String code,
            String name,
            String description,
            String status,
            String unit,
            int minStock,
            BigDecimal currentPrice,
            BigDecimal currentCost,
            long createdAt,
            long updatedAt
    ) {
        Medicine m = new Medicine(code, name, description, unit, minStock, currentPrice, currentCost);
        m.id = new MedicineId(id);
        m.status = MedicineStatus.safeValueOf(status);
        m.createdAt = createdAt;
        m.updatedAt = updatedAt;
        return m;
    }

    public void updateCatalogInfo(
            String name,
            String description,
            String unit,
            Integer minStock,
            BigDecimal currentPrice,
            BigDecimal currentCost
    ) {
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.unitType = UnitType.safeValueOf(unit);
        this.minStock = validateMinStock(minStock);
        this.currentPrice = validateMoneyValue(currentPrice, "precio actual");
        this.currentCost = validateMoneyValue(currentCost, "costo actual");
        this.updatedAt = Instant.now().toEpochMilli();
    }

    public void changePrice(BigDecimal newPrice) {
        this.currentPrice = validateMoneyValue(newPrice, "precio actual");
    }

    public void changeCost(BigDecimal newCost) {
        this.currentCost = validateMoneyValue(newCost, "costo actual");
    }

    public void deactivate() {
        if (this.status == MedicineStatus.INACTIVE) {
            throw new IllegalStateException("La medicina ya está inactiva");
        }
        this.status = MedicineStatus.INACTIVE;
    }

    public void activate() {
        if (this.status == MedicineStatus.ACTIVE) {
            throw new IllegalStateException("La medicina ya está activa");
        }
        this.status = MedicineStatus.ACTIVE;
    }


    private String validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (name.length() > 120) {
            throw new IllegalArgumentException("El nombre supera 120 caracteres");
        }
        return name.trim();
    }

    private String validateDescription(String description) {
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("La descripción supera 500 caracteres");
        }
        return description != null ? description.trim() : null;
    }

    private int validateMinStock(int minStock) {
        if (minStock < 0) {
            throw new IllegalArgumentException("El stock mínimo debe ser mayor o igual a cero");
        }
        return minStock;
    }

    private BigDecimal validateMoneyValue(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El valor de " + fieldName + " debe ser mayor o igual a cero");
        }
        return value;
    }

}