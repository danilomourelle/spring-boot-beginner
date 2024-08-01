package com.danmou.beginner.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.dao.EmployeeDAO;
import com.danmou.beginner.entity.Employee;


@Service
public class EmployeeServiceImpl implements EmployeeService {
  private EmployeeDAO employeeDAO;

  public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
    this.employeeDAO = employeeDAO;
  }

  @Override
  public List<Employee> findAll() {
    return employeeDAO.findAll();
  }

  @Override
  public Employee findById(int id) {
    return employeeDAO.findById(id);
  }

  @Transactional
  @Override
  public Employee save(Employee employee) {
    return employeeDAO.save(employee);
  }

  @Transactional
  @Override
  public void deleteById(int id) {
    employeeDAO.deleteById(id);
  }
}
