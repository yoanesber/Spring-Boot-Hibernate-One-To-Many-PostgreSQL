package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployee;

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class DepartmentEmployeeDTO {
    private String departmentId;
    private Date fromDate;
    private Date toDate;

    public DepartmentEmployeeDTO(DepartmentEmployee departmentEmployee) {
        if (departmentEmployee != null && departmentEmployee.getId() != null) {
            this.departmentId = departmentEmployee.getId().getDepartmentId();
        }
        
        this.fromDate = departmentEmployee.getFromDate();
        this.toDate = departmentEmployee.getToDate();
    }
}
