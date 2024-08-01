package com.danmou.beginner.dao;

import java.util.List;

import com.danmou.beginner.entity.Employee;

public interface EmployeeDAO {
  List<Employee> findAll();
}
