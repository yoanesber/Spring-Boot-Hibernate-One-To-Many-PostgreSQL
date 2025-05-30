package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Employee.
 * This class is used to transfer data between layers, such as from the service layer to the controller layer.
 * It contains only the necessary fields for transferring employee information.
 */

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
    private Boolean active;
    private Long createdBy;
    private Long updatedBy;

    private List<DepartmentEmployeeDTO> departments = new ArrayList<>();
    private List<SalaryEmployeeDTO> salaries = new ArrayList<>();
    private List<TitleEmployeeDTO> titles = new ArrayList<>();
}
