package com.danmou.beginner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danmou.beginner.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}