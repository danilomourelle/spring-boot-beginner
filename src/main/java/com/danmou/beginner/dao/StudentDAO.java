package com.danmou.beginner.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.entities.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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

  @Override
  public Student findById(Integer id) {
    Student student = entityManager.find(Student.class, id);

    return student;
  }

  @Override
  public List<Student> findAll() {
    TypedQuery<Student> query = entityManager.createQuery("FROM Student ORDER BY lastName", Student.class);
    List<Student> students = query.getResultList();

    return students;
  }

  @Override
  public List<Student> findByLastName(String lastName) {
    TypedQuery<Student> query = entityManager.createQuery("FROM Student WHERE lastName=:value1", Student.class);
    query.setParameter("value1", lastName);
    
    List<Student> students = query.getResultList();

    return students;
  }
}
