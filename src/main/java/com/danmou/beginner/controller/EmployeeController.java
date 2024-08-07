package com.danmou.beginner.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danmou.beginner.entity.Employee;
import com.danmou.beginner.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestParam;

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

  @GetMapping("/create-form")
  public String createForm(Model model) {
    Employee employee = new Employee();
    model.addAttribute("employee", employee);

    return "employees/create-form";
  }

  @PostMapping("/save")
  public String saveEmployee(@ModelAttribute Employee employee) {
    employeeService.save(employee);

    return "redirect:/employees/all";
  }

  @GetMapping("/update-form")
  public String updateForm(@RequestParam int id, Model model) {
    Employee employee = employeeService.findById(id);
    model.addAttribute("employee", employee);
    
    return "employees/create-form";
  }
}
