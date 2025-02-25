package com.yoanesber.spring.hibernate.one_to_many_postgresql.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployee;

@Data
@Getter
@Setter
@NoArgsConstructor // Required for Jackson deserialization when receiving JSON requests.
@AllArgsConstructor // Helps create DTO objects easily (useful when converting from entities).
public class TitleEmployeeDTO {
    private String title;
    private Date fromDate;
    private Date toDate;

    public TitleEmployeeDTO (TitleEmployee titleEmployee) {
        if (titleEmployee != null && titleEmployee.getId() != null)  {
            this.title = titleEmployee.getId().getTitle();
            this.fromDate = titleEmployee.getId().getFromDate();
        }

        this.toDate = titleEmployee.getToDate();
    }
}
