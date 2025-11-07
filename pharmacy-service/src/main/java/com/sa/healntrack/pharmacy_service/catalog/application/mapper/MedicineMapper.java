package com.sa.healntrack.pharmacy_service.catalog.application.mapper;

import com.sa.healntrack.pharmacy_service.catalog.application.port.in.create_medicine.CreateMedicineCommand;
import com.sa.healntrack.pharmacy_service.catalog.application.port.in.update_medicine.UpdateMedicineCommand;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MedicineMapper {

    public Medicine toDomain(CreateMedicineCommand command) {
        return new Medicine(
                command.code(),
                command.name(),
                command.description(),
                command.unit(),
                command.minStock(),
                command.currentPrice(),
                command.currentCost()
        );
    }

    public void updateDomain(Medicine existing, UpdateMedicineCommand command) {
        existing.updateCatalogInfo(
                command.name(),
                command.description(),
                command.unit(),
                command.minStock(),
                command.currentPrice(),
                command.currentCost()
        );
    }
}
