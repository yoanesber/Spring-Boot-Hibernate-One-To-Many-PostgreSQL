package com.yoanesber.spring.hibernate.one_to_many_postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployeeId;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployeeId> {

}
