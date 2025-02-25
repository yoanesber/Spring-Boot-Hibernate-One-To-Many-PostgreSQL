package com.yoanesber.spring.hibernate.one_to_many_postgresql.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "salary") // name of the table in the database
public class SalaryEmployee {
    @EmbeddedId
    private SalaryEmployeeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    // Constructor to ensure ID is properly set
    public SalaryEmployee(Employee employee, Date fromDate) {
        this.employee = employee;
        this.id = new SalaryEmployeeId(employee.getId(), fromDate); // Ensure ID is set
    }
}
