package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.get_all_sales.GetAllSalesQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_all_medicines.CatalogGetAllMedicines;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_all_medicines.CatalogGetAllMedicinesQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.FindSales;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees.Employee;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees.GetAllEmployees;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.hospitalization.get_all_hospitalizations.GetAllHospitalizations;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.hospitalization.get_all_hospitalizations.Hospitalization;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_all_patients.Patient;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_all_patients.GetAllPatients;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleBuyer;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleItemMedicine;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleSeller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllSalesImpl implements GetAllSales {

    private final FindSales findSales;
    private final GetAllPatients getAllPatients;
    private final GetAllEmployees getAllEmployees;
    private final GetAllHospitalizations getAllHospitalizations;
    private final CatalogGetAllMedicines getAllMedicines;

    @Override
    public List<Sale> handle(GetAllSalesQuery q) {
        List<Sale> sales = findSales.search(q.occurredFrom(), q.occurredTo(), q.sellerId(), q.status());
        Map<UUID, Employee> employeeById = loadEmployees();
        Map<UUID, Medicine> medicineById = loadMedicines();
        Map<UUID, Patient> patientsById = loadPatients();
        Map<UUID, Hospitalization> hospitalizationsById = loadHospitalizations();

        for (Sale s : sales) {
            Employee seller = employeeById.get(s.getSellerId().value());
            if (seller != null) {
                s.setSeller(new SaleSeller(
                        seller.cui(),
                        seller.fullname(),
                        seller.isActive()
                ));
            }

            switch (s.getBuyerType()) {

                case PATIENT -> {
                    Patient patient = patientsById.get(s.getBuyerId().value());
                    setBuyerIfPresent(s, patient);
                }

                case HOSPITALIZATION -> {
                    Hospitalization h = hospitalizationsById.get(s.getBuyerId().value());
                    if (h != null) {
                        Patient patient = patientsById.get(h.patientId());
                        setBuyerIfPresent(s, patient);
                    }
                }

                default -> s.setBuyer(null);
            }

            for (SaleItem item : s.getItems()) {
                Medicine m = medicineById.get(item.getMedicineId().value());
                if (m != null) {
                    item.setMedicine(new SaleItemMedicine(
                            m.getName(),
                            m.getStatus().name()
                    ));
                }
            }
        }

        return sales;
    }

    private Map<UUID, Employee> loadEmployees() {
        return getAllEmployees.getAll()
                .stream()
                .collect(Collectors.toMap(Employee::id, e -> e));
    }

    private Map<UUID, Patient> loadPatients() {
        return getAllPatients.getAll()
                .stream()
                .collect(Collectors.toMap(Patient::id, e -> e));
    }

    private Map<UUID, Hospitalization> loadHospitalizations() {
        return getAllHospitalizations.getAll()
                .stream()
                .collect(Collectors.toMap(Hospitalization::id, e -> e));
    }

    private Map<UUID, Medicine> loadMedicines() {
        return getAllMedicines.getAll(new CatalogGetAllMedicinesQuery(null, null))
                .stream()
                .collect(Collectors.toMap(m -> m.getId().value(), m -> m));
    }

    private void setBuyerIfPresent(Sale sale, Patient patient) {
        if (patient != null) {
            sale.setBuyer(new SaleBuyer(
                    patient.cui(),
                    patient.fullName()
            ));
        }
    }
}
