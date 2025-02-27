package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployee;

public interface TitleEmployeeService {
    // Save title employee
    TitleEmployee saveTitleEmployee(TitleEmployee titleEmployee);
}
