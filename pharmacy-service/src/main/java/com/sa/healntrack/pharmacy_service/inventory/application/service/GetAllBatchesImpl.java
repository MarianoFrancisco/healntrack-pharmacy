package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_all_batches.GetAllBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_all_batches.GetAllBatchesQuery;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.integration.get_all_medicines.CatalogGetAllMedicines;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.integration.get_all_medicines.CatalogGetAllMedicinesQuery;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_all_employees.Employee;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_all_employees.GetAllEmployees;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.BatchMedicine;
import com.sa.healntrack.pharmacy_service.inventory.domain.value_object.BatchEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllBatchesImpl implements GetAllBatches {

    private final FindBatches findBatches;
    private final GetAllEmployees getAllEmployees;
    private final CatalogGetAllMedicines getAllMedicines;

    @Override
    public List<Batch> handle(GetAllBatchesQuery query) {
        List<Batch> batches = findBatches.findAll(query.onlyWithStock(), query.onlyNotExpired());
        Map<UUID, Employee> employeeById = loadEmployees();
        Map<UUID, Medicine> medicineById = loadMedicines();
        batches = filterByMedicineCode(batches, medicineById, query.medicineCode());
        enrichBatches(batches, medicineById, employeeById);
        return batches;
    }

    private Map<UUID, Employee> loadEmployees() {
        return getAllEmployees.getAll()
                .stream()
                .collect(Collectors.toMap(Employee::id, e -> e));
    }

    private Map<UUID, Medicine> loadMedicines() {
        return getAllMedicines.getAll(new CatalogGetAllMedicinesQuery(null, null))
                .stream()
                .collect(Collectors.toMap(m -> m.getId().value(), m -> m));
    }

    private List<Batch> filterByMedicineCode(
            List<Batch> batches,
            Map<UUID, Medicine> medicineById,
            String codeFilter
    ) {
        if (codeFilter == null || codeFilter.isBlank()) {
            return batches;
        }

        String prefix = codeFilter.trim().toUpperCase();

        Set<UUID> allowedMedicineIds = medicineById.values().stream()
                .filter(m -> m.getCode().value() != null &&
                        m.getCode().value().toUpperCase().contains(prefix))
                .map(m -> m.getId().value())
                .collect(Collectors.toSet());

        return batches.stream()
                .filter(b -> allowedMedicineIds.contains(b.getMedicineId().value()))
                .collect(Collectors.toList());
    }

    private void enrichBatches(
            List<Batch> batches,
            Map<UUID, Medicine> medicineById,
            Map<UUID, Employee> employeeById
    ) {
        for (Batch batch : batches) {
            Medicine med = medicineById.get(batch.getMedicineId().value());
            if (med != null) {
                batch.setMedicine(new BatchMedicine(
                        med.getCode().value(),
                        med.getName(),
                        med.getStatus().name()
                ));
            }

            Employee emp = employeeById.get(batch.getPurchasedBy().value());
            if (emp != null) {
                batch.setEmployee(new BatchEmployee(
                        emp.cui(),
                        emp.fullname(),
                        emp.isActive()
                ));
            }
        }
    }
}
