package com.danmou.beginner.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.entity.Employee;
import com.danmou.beginner.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping()
  public List<Employee> findAllEmployees() {
    return employeeService.findAll();
  }

  @GetMapping("/{id}")
  public Employee findEmployeeById(@PathVariable int id) {
    Employee employee = employeeService.findById(id);
    if (employee == null) {
      throw new RuntimeException("Employee not found for id " + id);
    }

    return employee;
  }
}
