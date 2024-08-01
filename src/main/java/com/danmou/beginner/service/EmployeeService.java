package com.danmou.beginner.service;

import java.util.List;

import com.danmou.beginner.entity.Employee;

public interface EmployeeService {
  List<Employee> findAll();
}
