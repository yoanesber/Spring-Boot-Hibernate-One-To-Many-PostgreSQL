package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import java.util.List;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;

public interface DepartmentService {
    void saveDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAllDepartments();
    Department getDepartmentById(String id);
    DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO);
    void deleteDepartment(String id);
}
