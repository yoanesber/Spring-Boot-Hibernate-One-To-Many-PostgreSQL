package com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.EmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Employee;

/**
 * Mapper class for converting between Employee and EmployeeDTO.
 * This class provides methods to convert an Employee entity to a DTO and vice versa.
 */

public class EmployeeMapper {
    public static EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getBirthDate(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getGender(),
                employee.getHireDate(),
                employee.getActive(),
                employee.getCreatedBy(),
                employee.getUpdatedBy(),
                employee.getDepartments().stream()
                    .map(DepartmentEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new)),
                employee.getSalaries().stream()
                    .map(SalaryEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new)),
                employee.getTitles().stream()
                    .map(TitleEmployeeMapper::toDTO)
                    .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public static List<EmployeeDTO> toDTOList(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return List.of();
        }

        return employees.stream()
            .map(EmployeeMapper::toDTO)
            .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static Employee toEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setActive(employeeDTO.getActive());
        employee.setCreatedBy(employeeDTO.getCreatedBy());
        employee.setUpdatedBy(employeeDTO.getUpdatedBy());
        employee.setDepartments(
            employeeDTO.getDepartments().stream()
                .map(DepartmentEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));
        employee.setSalaries(
            employeeDTO.getSalaries().stream()
                .map(SalaryEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));
        employee.setTitles(
            employeeDTO.getTitles().stream()
                .map(TitleEmployeeMapper::toEntity)
                .collect(Collectors.toCollection(ArrayList::new)));

        return employee;
    }

    public static List<Employee> toEntityList(List<EmployeeDTO> employeeDTOs) {
        if (employeeDTOs == null || employeeDTOs.isEmpty()) {
            return new ArrayList<>();
        }

        return employeeDTOs.stream()
            .map(EmployeeMapper::toEntity)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
