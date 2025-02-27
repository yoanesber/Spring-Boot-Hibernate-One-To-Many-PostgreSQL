package com.yoanesber.spring.hibernate.one_to_many_postgresql.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.repository.TitleEmployeeRepository;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.TitleEmployeeService;

@Service
public class TitleEmployeeServiceImpl implements TitleEmployeeService {
    
    private final TitleEmployeeRepository titleEmployeeRepository;

    public TitleEmployeeServiceImpl(TitleEmployeeRepository titleEmployeeRepository) {
        this.titleEmployeeRepository = titleEmployeeRepository;
    }

    @Override
    @Transactional
    public TitleEmployee saveTitleEmployee(TitleEmployee titleEmployee) {
        Assert.notNull(titleEmployee, "TitleEmployee cannot be null");

        try {
            return titleEmployeeRepository.save(titleEmployee);
        } catch (Exception e) {
            throw new RuntimeException("Error saving titleEmployee: " + e.getMessage());
        }
    }
}
