package com.yoanesber.spring.hibernate.one_to_many_postgresql.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor  // Mandatory for JPA (Hibernate needs a no-arg constructor to create objects).
@AllArgsConstructor // Useful for creating objects manually
@Embeddable
public class SalaryEmployeeId {
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    
    @Column(name = "from_date", nullable = false)
    private Date fromDate;
}
