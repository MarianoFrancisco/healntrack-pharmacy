package com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_all_employees;

import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.Employee;

import java.util.List;

public interface GetAllEmployees {
    List<Employee> getAll();
}
