package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;

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
    private Timestamp createdDate;
    private Long updatedBy;
    private Timestamp updatedDate;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.deptName = department.getDeptName();
        this.active = department.getActive();
        this.createdBy = department.getCreatedBy();
        this.createdDate = department.getCreatedDate();
        this.updatedBy = department.getUpdatedBy();
        this.updatedDate = department.getUpdatedDate();
    }
}
