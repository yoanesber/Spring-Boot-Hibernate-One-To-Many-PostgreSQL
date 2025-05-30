package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import java.util.List;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;

/**
 * Service interface for managing departments.
 * This interface defines methods for saving, retrieving, updating, and deleting departments.
 * It provides a contract for the implementation of department-related business logic.
 */

public interface DepartmentService {
    // Save department
    Department saveDepartment(Department department);

    // Get all departments
    List<Department> getAllDepartments();

    // Get department by id
    Department getDepartmentById(String id);

    // Update department
    Department updateDepartment(String id, Department department);

    // Delete department
    Boolean deleteDepartment(String id);
}
