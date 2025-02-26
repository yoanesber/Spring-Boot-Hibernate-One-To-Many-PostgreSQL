package com.yoanesber.spring.hibernate.one_to_many_postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployeeId;

@Repository
public interface SalaryEmployeeRepository extends JpaRepository<SalaryEmployee, SalaryEmployeeId> {

}
