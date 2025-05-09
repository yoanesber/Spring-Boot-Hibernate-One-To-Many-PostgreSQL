package com.yoanesber.spring.hibernate.one_to_many_postgresql.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.repository.SalaryEmployeeRepository;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.SalaryEmployeeService;

@Service
public class SalaryEmployeeServiceImpl implements SalaryEmployeeService {

    private final SalaryEmployeeRepository salaryEmployeeRepository;

    public SalaryEmployeeServiceImpl(SalaryEmployeeRepository salaryEmployeeRepository) {
        this.salaryEmployeeRepository = salaryEmployeeRepository;
    }

    @Override
    @Transactional
    public SalaryEmployee saveSalaryEmployee(SalaryEmployee salaryEmployee) {
        Assert.notNull(salaryEmployee, "SalaryEmployee cannot be null");

        return salaryEmployeeRepository.save(salaryEmployee);
    }
}
