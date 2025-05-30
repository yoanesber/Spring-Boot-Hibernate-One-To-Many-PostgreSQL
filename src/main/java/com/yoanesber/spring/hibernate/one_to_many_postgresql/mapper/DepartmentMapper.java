package com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;

/**
 * Mapper class for converting between Department and DepartmentDTO.
 * This class provides methods to convert a Department entity to a DTO and vice versa.
 */

public class DepartmentMapper {
    public static DepartmentDTO toDTO(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDTO(
                department.getId(),
                department.getDeptName(),
                department.getActive(),
                department.getCreatedBy(),
                department.getUpdatedBy()
        );
    }

    public static List<DepartmentDTO> toDTOList(List<Department> departments) {
        if (departments == null || departments.isEmpty()) {
            return List.of();
        }

        return departments.stream()
                .map(DepartmentMapper::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static Department toEntity(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            return null;
        }

        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setDeptName(departmentDTO.getDeptName());
        department.setActive(departmentDTO.getActive());
        department.setCreatedBy(departmentDTO.getCreatedBy());
        department.setUpdatedBy(departmentDTO.getUpdatedBy());

        return department;
    }

    public static List<Department> toEntityList(List<DepartmentDTO> departmentDTOs) {
        if (departmentDTOs == null || departmentDTOs.isEmpty()) {
            return List.of();
        }

        return departmentDTOs.stream()
                .map(DepartmentMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
