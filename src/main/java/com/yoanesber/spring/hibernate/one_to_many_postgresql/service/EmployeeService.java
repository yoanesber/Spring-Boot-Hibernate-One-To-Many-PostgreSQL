package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import java.util.List;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.EmployeeDTO;

public interface EmployeeService {
    // Save employee
    void saveEmployee(EmployeeDTO employeeDTO);

    // Get all employees
    List<EmployeeDTO> getAllEmployees();

    // Get employee by id
    EmployeeDTO getEmployeeById(Long id);

    // Update employee
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employee);

    // Delete employee
    Boolean deleteEmployee(Long id);
}
