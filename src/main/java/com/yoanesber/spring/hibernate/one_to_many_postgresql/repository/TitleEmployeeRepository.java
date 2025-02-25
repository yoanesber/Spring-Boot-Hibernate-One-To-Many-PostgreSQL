package com.yoanesber.spring.hibernate.one_to_many_postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployeeId;

public interface TitleEmployeeRepository extends JpaRepository<TitleEmployee, TitleEmployeeId> {

}
