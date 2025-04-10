package com.yoanesber.spring.hibernate.one_to_many_postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.repository.DepartmentEmployeeRepository;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.DepartmentEmployeeService;

@Service
public class DepartmentEmployeeServiceImpl implements DepartmentEmployeeService {

    private final DepartmentEmployeeRepository departmentEmployeeRepository;

    public DepartmentEmployeeServiceImpl(DepartmentEmployeeRepository departmentEmployeeRepository) {
        this.departmentEmployeeRepository = departmentEmployeeRepository;
    }

    @Override
    @Transactional
    public DepartmentEmployee saveDepartmentEmployee(DepartmentEmployee departmentEmployee) {
        Assert.notNull(departmentEmployee, "DepartmentEmployee cannot be null");

        return departmentEmployeeRepository.save(departmentEmployee);
    }
}
