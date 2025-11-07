package com.sa.healntrack.pharmacy_service.inventory.infrastructure.adapter.out.rest.employee.mapper;

import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.Department;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.Employee;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
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
