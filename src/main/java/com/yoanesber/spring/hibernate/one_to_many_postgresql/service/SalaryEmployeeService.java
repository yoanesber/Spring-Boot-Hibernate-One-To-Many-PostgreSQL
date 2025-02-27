package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployee;

public interface SalaryEmployeeService {
    // Save salary employee
    SalaryEmployee saveSalaryEmployee(SalaryEmployee salaryEmployee);
}
