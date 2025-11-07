package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.employee;

import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees.Employee;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees.GetAllEmployees;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.employee.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesEmployeeApi implements GetAllEmployees {

    private final RestClient employeeRestClient;

    @Override
    public List<Employee> getAll() {
        List<EmployeeResponseDTO> response = employeeRestClient.get()
                .uri("/api/v1/employees")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response == null) response = List.of();
        return EmployeeMapper.toApplication(response);
    }
}
