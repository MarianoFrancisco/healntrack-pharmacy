package com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_employees_by_code;

import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.Employee;

import java.util.List;

public interface GetEmployeesByCode {
    List<Employee> getByCode();
}
