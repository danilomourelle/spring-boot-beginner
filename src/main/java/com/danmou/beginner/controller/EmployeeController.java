package com.danmou.beginner.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danmou.beginner.entity.Employee;
import com.danmou.beginner.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/all")
  public String list(Model model) {
    List<Employee> employees = employeeService.findAll();
    model.addAttribute("employees", employees);

    return "employees/list-employees";
  }
}
