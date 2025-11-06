package com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.mapper;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.create_medicine.CreateMedicineCommand;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.update_medicine.UpdateMedicineCommand;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.catalog.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MedicineRestMapper {

    public CreateMedicineCommand toCommand(CreateMedicineDTO d) {
        return new CreateMedicineCommand(
                d.code(), d.name(), d.description(), d.unitType(),
                d.minStock(), d.currentPrice(), d.currentCost()
        );
    }

    public UpdateMedicineCommand toCommand(UpdateMedicineDTO d) {
        return new UpdateMedicineCommand(
                d.name(), d.description(), d.unitType(),
                d.minStock(), d.currentPrice(), d.currentCost()
        );
    }

    public MedicineResponseDTO toResponse(Medicine m) {
        return new MedicineResponseDTO(
                m.getId().value(), m.getCode().value(), m.getName(), m.getDescription(),
                m.getStatus(), m.getUnitType(), m.getMinStock(), m.getCurrentPrice(), m.getCurrentCost(),
                m.getCreatedAt(), m.getUpdatedAt(), m.getStock()
        );
    }
}
