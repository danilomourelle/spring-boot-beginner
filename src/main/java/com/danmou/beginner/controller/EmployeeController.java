package com.danmou.beginner.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.entity.Employee;
import com.danmou.beginner.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

  @PostMapping()
  public Employee addEmployee(@RequestBody Employee newEmployee) {
    newEmployee.setId(null);

    return employeeService.save(newEmployee);
  }

  @PutMapping("/{id}")
  public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employeeToUpdate) {
    System.out.println(employeeToUpdate);
    employeeToUpdate.setId(id);

    return employeeService.save(employeeToUpdate);
  }

  @DeleteMapping("/{id}")
  public String deleteEmployee(@PathVariable int id) {
    Employee existingEmployee = employeeService.findById(id);
    if (existingEmployee == null) {
      throw new RuntimeException("Employee with id " + id + " does not exists");
    }

    employeeService.deleteById(id);

    return "Deleted Employee with id " + id ;
  }
}
