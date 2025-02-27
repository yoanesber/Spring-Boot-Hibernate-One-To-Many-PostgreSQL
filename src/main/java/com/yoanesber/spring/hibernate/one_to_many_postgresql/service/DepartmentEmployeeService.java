package com.yoanesber.spring.hibernate.one_to_many_postgresql.service;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployee;

public interface DepartmentEmployeeService {
    // Save department employee
    DepartmentEmployee saveDepartmentEmployee(DepartmentEmployee departmentEmployee);
}
