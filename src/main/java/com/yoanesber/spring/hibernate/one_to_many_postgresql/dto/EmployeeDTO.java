package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Employee;

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class EmployeeDTO {
    private Long id;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private Date hireDate;
    private Boolean activeStatus;
    private Long createdBy;
    private Timestamp createdDate;
    private Long updatedBy;
    private Timestamp updatedDate;
    private List<DepartmentEmployeeDTO> departments = new ArrayList<>();
    private List<SalaryEmployeeDTO> salaries = new ArrayList<>();
    private List<TitleEmployeeDTO> titles = new ArrayList<>();

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.birthDate = employee.getBirthDate();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName(); 
        this.gender = employee.getGender();
        this.hireDate = employee.getHireDate();
        this.activeStatus = employee.getActiveStatus();
        this.createdBy = employee.getCreatedBy();
        this.createdDate = employee.getCreatedDate();
        this.updatedBy = employee.getUpdatedBy();
        this.updatedDate = employee.getUpdatedDate();
        this.departments = employee.getDepartments().stream().map(DepartmentEmployeeDTO::new).collect(Collectors.toList());
        this.salaries = employee.getSalaries().stream().map(SalaryEmployeeDTO::new).collect(Collectors.toList());
        this.titles = employee.getTitles().stream().map(TitleEmployeeDTO::new).collect(Collectors.toList());
    }
}
