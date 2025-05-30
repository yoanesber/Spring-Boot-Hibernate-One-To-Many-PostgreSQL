package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for SalaryEmployee.
 * This class is used to transfer data between layers, such as from the service layer to the controller layer.
 * It contains only the necessary fields for transferring salary employee information.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class SalaryEmployeeDTO {
    private Date fromDate;
    private Long amount;
    private Date toDate;
}
