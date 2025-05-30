package com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.SalaryEmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployeeId;

/**
 * Mapper class for converting between SalaryEmployee and SalaryEmployeeDTO.
 * This class provides methods to convert a SalaryEmployee entity to a DTO and vice versa.
 */

public class SalaryEmployeeMapper {
    public static SalaryEmployeeDTO toDTO(SalaryEmployee salaryEmployee) {
        if (salaryEmployee == null) {
            return null;
        }

        return new SalaryEmployeeDTO(
                salaryEmployee.getId().getFromDate(),
                salaryEmployee.getAmount(),
                salaryEmployee.getToDate()
        );
    }

    public static SalaryEmployee toEntity(SalaryEmployeeDTO salaryEmployeeDTO) {
        if (salaryEmployeeDTO == null) {
            return null;
        }

        SalaryEmployee salaryEmployee = new SalaryEmployee();
        salaryEmployee.setId(new SalaryEmployeeId(
                null,
                salaryEmployeeDTO.getFromDate()
        ));
        salaryEmployee.setAmount(salaryEmployeeDTO.getAmount());
        salaryEmployee.setToDate(salaryEmployeeDTO.getToDate());

        return salaryEmployee;
    }
}
