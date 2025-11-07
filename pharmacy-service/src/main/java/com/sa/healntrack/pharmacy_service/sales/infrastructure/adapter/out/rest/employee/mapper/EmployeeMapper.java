package com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.rest.employee.mapper;

import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees.Department;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.employee.get_all_employees.Employee;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EmployeeMapper {
    public List<Employee> toApplication(List<EmployeeResponseDTO> response) {
        return response.stream().map(
                EmployeeMapper::toApplication
        ).toList();
    }

    public Employee toApplication(EmployeeResponseDTO response) {
        return new Employee(
                response.id(),
                response.cui(),
                response.fullname(),
                response.email(),
                new Department(
                        response.department().name(),
                        response.department().description(),
                        response.department().isActive()
                ),
                response.isActive()
        );
    }
}
