package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.rest.employee;

import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.Employee;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_all_employees.GetAllEmployees;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_employees_by_code.GetEmployeesByCode;
import com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.rest.employee.mapper.EmployeeMapper;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
@RequiredArgsConstructor
public class InventoryEmployeeApi implements GetAllEmployees, GetEmployeesByCode {

    private final RestClient employeeRestClient;

    @Override
    public List<Employee> getAll() {
        List<EmployeeResponseDTO> response = employeeRestClient.get()
                .uri("/api/v1/employees")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return response == null ? List.of() : EmployeeMapper.toApplication(response);
    }

    @Override
    public List<Employee> getByCode() {
        List<EmployeeResponseDTO> response = employeeRestClient.get()

                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employees")
                        .queryParam("department", "FAR-025")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return response == null ? List.of() : EmployeeMapper.toApplication(response);
    }
}
