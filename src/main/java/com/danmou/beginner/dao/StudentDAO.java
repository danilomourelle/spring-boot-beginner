package com.danmou.beginner.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.entities.Student;

import jakarta.persistence.EntityManager;

@Repository
public class StudentDAO implements IStudentDAO {

  private EntityManager entityManager;

  @Autowired
  public StudentDAO(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void save(Student student) {
    entityManager.persist(student);
  }
}
