package com.danmou.beginner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.danmou.beginner.entity.Employee;
import com.danmou.beginner.repository.EmployeeRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService {
  private EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee findById(int id) {
    return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Foo"));
  }

  @Override
  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }

  @Override
  public void deleteById(int id) {
    employeeRepository.deleteById(id);
  }
}