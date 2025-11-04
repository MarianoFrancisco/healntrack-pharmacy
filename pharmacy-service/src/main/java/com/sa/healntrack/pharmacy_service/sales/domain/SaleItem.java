package com.sa.healntrack.pharmacy_service.sales.domain;

import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineCode;
import com.sa.healntrack.pharmacy_service.catalog.domain.value_object.MedicineId;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Money;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleItemId;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class SaleItem {
    private SaleItemId id;
    private MedicineId medicineId;
    private MedicineCode medicineCode;
    private int quantity;
    private Money unitPrice;
    private Money unitCost;
    private Money lineTotal;

    public SaleItem(UUID medicineId, String medicineCode, int quantity, BigDecimal unitPrice, BigDecimal unitCost) {
        this.medicineId = new MedicineId(medicineId);
        this.medicineCode = new MedicineCode(medicineCode);
        this.quantity = validateQuantity(quantity);
        this.unitPrice = new Money(unitPrice);
        this.unitCost = new Money(unitCost);
        this.lineTotal = this.unitPrice.multiply(quantity);
    }

    public static SaleItem restore(UUID id, UUID medicineId, String medicineCode, int quantity,
                                   BigDecimal unitPrice, BigDecimal unitCost, BigDecimal lineTotal) {
        SaleItem si = new SaleItem(medicineId, medicineCode, quantity, unitPrice, unitCost);
        si.id = new SaleItemId(id);
        si.lineTotal = new Money(lineTotal);
        return si;
    }

    private int validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor o igual a cero");
        }
        return quantity;
    }


}
