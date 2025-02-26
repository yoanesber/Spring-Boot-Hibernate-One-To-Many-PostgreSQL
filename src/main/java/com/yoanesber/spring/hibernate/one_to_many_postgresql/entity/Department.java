package com.yoanesber.spring.hibernate.one_to_many_postgresql.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "department")    // name of the table in the database
public class Department {
    @Id
    @Column(name = "id", nullable = false, length = 4)
    private String id;

    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentEmployee> departmentEmployees = new ArrayList<>();
}
