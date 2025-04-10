package com.yoanesber.spring.hibernate.one_to_many_postgresql.service.impl;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.repository.DepartmentRepository;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void saveDepartment(DepartmentDTO departmentDTO) {
        Assert.notNull(departmentDTO, "Department cannot be null");

        // Get existing department
        Department existingDepartment = departmentRepository.findById(departmentDTO.getId())
            .orElse(null);

        // Check if department exists
        if (existingDepartment != null) {
            throw new IllegalArgumentException("Department with id " + departmentDTO.getId() + " already exists");
        } 
        
        // Prepare department entity
        Department department = new Department();
        department.setId(departmentDTO.getId().toLowerCase());
        department.setDeptName(departmentDTO.getDeptName());
        department.setActive(null != departmentDTO.getActive() ? departmentDTO.getActive() : true);
        department.setCreatedBy((Long)departmentDTO.getCreatedBy());
        department.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedBy((Long)departmentDTO.getUpdatedBy());
        department.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

        // Save department
        departmentRepository.save(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        // Get all departments
        List<Department> departments = departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // Check if departments is empty
        if (departments.isEmpty()) {
            return List.of();
        }

        // Return departments
        return departments.stream().map(DepartmentDTO::new).toList();
    }

    @Override
    public Department getDepartmentById(String id) {
        Assert.hasText(id, "Department id cannot be null or empty");

        // Get department by id
        Department department = departmentRepository.findById(id)
            .orElse(null);

        // Check if department is null
        if (department == null) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        }

        // Return department
        return department;
    }

    @Override
    @Transactional
    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
        Assert.notNull(departmentDTO, "Department cannot be null");
        Assert.hasText(id, "Department id cannot be null or empty");

        // Get existing department
        Department existingDepartment = departmentRepository.findById(id)
            .orElse(null);

        // Check if department exists
        if (existingDepartment == null) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        } 
        
        // Update department
        existingDepartment.setDeptName(departmentDTO.getDeptName());
        existingDepartment.setActive(null != departmentDTO.getActive() ? departmentDTO.getActive() : existingDepartment.getActive());
        existingDepartment.setUpdatedBy((Long)departmentDTO.getUpdatedBy());
        existingDepartment.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

        // Save department
        return new DepartmentDTO(departmentRepository.save(existingDepartment));
    }

    @Override
    @Transactional
    public Boolean deleteDepartment(String id) {
        Assert.hasText(id, "Department id cannot be null or empty");

        // Get existing department
        Department existingDepartment = departmentRepository.findById(id)
            .orElse(null);

        // Check if department exists
        if (existingDepartment == null) {
            throw new IllegalArgumentException("Department with id " + id + " does not exist");
        }

        // Delete department
        departmentRepository.deleteById(id);
        
        return true;
    }

}
