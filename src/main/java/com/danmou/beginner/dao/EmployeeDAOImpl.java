package com.danmou.beginner.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.danmou.beginner.entity.Employee;

import jakarta.persistence.EntityManager;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
  private EntityManager entityManager;

  public EmployeeDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Employee> findAll() {
    return entityManager
        .createQuery("FROM Employee", Employee.class)
        .getResultList();
  }

  @Override
  public Employee findById(int id) {
    return entityManager.find(Employee.class, id);
  }

  @Override
  public Employee save(Employee employee) {
    return entityManager.merge(employee);
  }
}
