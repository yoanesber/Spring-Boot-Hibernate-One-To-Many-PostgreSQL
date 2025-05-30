package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import java.util.List;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Employee;

/**
 * Service interface for managing employees.
 * This interface defines methods for saving, retrieving, updating, and deleting employees.
 * It provides a contract for the implementation of employee-related business logic.
 */

public interface EmployeeService {
    // Save employee
    Employee saveEmployee(Employee employee);

    // Get all employees
    List<Employee> getAllEmployees();

    // Get employee by id
    Employee getEmployeeById(Long id);

    // Update employee
    Employee updateEmployee(Long id, Employee employee);

    // Delete employee
    Boolean deleteEmployee(Long id);
}
