package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import java.util.List;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;

public interface DepartmentService {
    // Save department
    void saveDepartment(DepartmentDTO departmentDTO);

    // Get all departments
    List<DepartmentDTO> getAllDepartments();

    // Get department by id
    Department getDepartmentById(String id);

    // Update department
    DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO);

    // Delete department
    Boolean deleteDepartment(String id);
}
