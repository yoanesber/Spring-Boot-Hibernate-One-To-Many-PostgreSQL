package com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.TitleEmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployeeId;

/**
 * Mapper class for converting between TitleEmployee and TitleEmployeeDTO.
 * This class provides methods to convert a TitleEmployee entity to a DTO and vice versa.
 */

public class TitleEmployeeMapper {
    public static TitleEmployeeDTO toDTO(TitleEmployee titleEmployee) {
        if (titleEmployee == null) {
            return null;
        }

        return new TitleEmployeeDTO(
                titleEmployee.getId().getTitle(),
                titleEmployee.getId().getFromDate(),
                titleEmployee.getToDate()
        );
    }

    public static TitleEmployee toEntity(TitleEmployeeDTO titleEmployeeDTO) {
        if (titleEmployeeDTO == null) {
            return null;
        }

        TitleEmployee titleEmployee = new TitleEmployee();
        titleEmployee.setId(new TitleEmployeeId(
                null,
                titleEmployeeDTO.getTitle(),
                titleEmployeeDTO.getFromDate()
        ));
        titleEmployee.setToDate(titleEmployeeDTO.getToDate());

        return titleEmployee;
    }
}
