package com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentEmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployeeId;

/**
 * Mapper class for converting between DepartmentEmployee and DepartmentEmployeeDTO.
 * This class provides methods to convert a DepartmentEmployee entity to a DTO and vice versa.
 */

public class DepartmentEmployeeMapper {
    public static DepartmentEmployeeDTO toDTO(DepartmentEmployee departmentEmployee) {
        if (departmentEmployee == null) {
            return null;
        }

        return new DepartmentEmployeeDTO(
                departmentEmployee.getId().getDepartmentId(),
                departmentEmployee.getFromDate(),
                departmentEmployee.getToDate()
        );
    }

    public static DepartmentEmployee toEntity(DepartmentEmployeeDTO departmentEmployeeDTO) {
        if (departmentEmployeeDTO == null) {
            return null;
        }

        DepartmentEmployee departmentEmployee = new DepartmentEmployee();
        departmentEmployee.setId(new DepartmentEmployeeId(
                null,
                departmentEmployeeDTO.getDepartmentId()
        ));
        departmentEmployee.setFromDate(departmentEmployeeDTO.getFromDate());
        departmentEmployee.setToDate(departmentEmployeeDTO.getToDate());

        return departmentEmployee;
    }
}
