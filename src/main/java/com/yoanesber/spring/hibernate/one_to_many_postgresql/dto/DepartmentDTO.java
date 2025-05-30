package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Department entity.
 * This class is used to transfer data between the application layers,
 * particularly when sending or receiving JSON data in RESTful APIs.
 */

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class DepartmentDTO {
    private String id;
    private String deptName;
    private Boolean active;
    private Long createdBy;
    private Long updatedBy;
}
