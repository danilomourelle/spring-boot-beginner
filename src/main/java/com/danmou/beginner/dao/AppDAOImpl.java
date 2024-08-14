package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;

import jakarta.persistence.EntityManager;

@Repository
public class AppDAOImpl implements AppDAO {

  private EntityManager entityManager;

  public AppDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void save(Instructor instructor) {
    entityManager.persist(instructor);
  }

  @Override
  public Instructor findInstructorById(int id) {
    return entityManager.find(Instructor.class, id);
  }

  @Override
  @Transactional
  public void deleteInstructorById(int id) {
    Instructor instructor = entityManager.getReference(Instructor.class, id);
    entityManager.remove(instructor);
  }

  @Override
  public InstructorDetail findInstructorDetailById(int id) {
    return entityManager.find(InstructorDetail.class, id);
  }

  @Override
  @Transactional
  public void deleteInstructorDetailById(int id) {
    InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, id);
    entityManager.remove(instructorDetail);
  }
}
