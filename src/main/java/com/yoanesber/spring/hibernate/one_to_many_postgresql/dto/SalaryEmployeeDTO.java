package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployee;

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class SalaryEmployeeDTO {
    private Date fromDate;
    private Long amount;
    private Date toDate;

    public SalaryEmployeeDTO (SalaryEmployee salaryEmployee) {
        if (salaryEmployee != null && salaryEmployee.getId() != null) {
            this.fromDate = salaryEmployee.getId().getFromDate();
        }

        this.amount = salaryEmployee.getAmount();
        this.toDate = salaryEmployee.getToDate();
    }
}
