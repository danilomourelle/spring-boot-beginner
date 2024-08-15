package com.danmou.beginner.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.entity.Course;
import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
    /**
     * If 'getReference' is used the query do retrieve data is not immediately executed,
     * instead, ORM will keep watching it, and just query when necessary.
     * For example, in this method, it will print "Lazy" message before select instructor,
     * but if replace 'getReference' by 'find' select is executed before message.
     */
    Instructor instructor = entityManager.getReference(Instructor.class, id);

    /**
     * Since course is linked to instructor,
     * it couses instructor should be set to null before removal.
     * Any attempt to remove instructor without detach it from course, 
     * it will throw an error of constraint.
     */
    System.out.println("Lazy fetch should happens");
    for (Course course : instructor.getCourses()) {
      course.setInstructor(null);
    }

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

  @Override
  public List<Course> findCoursesByInstructorId(int id) {
    TypedQuery<Course> query = entityManager
        .createQuery("FROM Course WHERE instructor.id = :data", Course.class)
        .setParameter("data", id);

    List<Course> courses = query.getResultList();

    System.out.println("Courses" + courses);

    return courses;
  }

  @Override
  public Instructor findInstructorByIdJoinFetch(int id) {
    TypedQuery<Instructor> query = entityManager
        .createQuery("SELECT i FROM Instructor i JOIN FETCH i.courses JOIN FETCH i.instructorDetail WHERE i.id = :data",
            Instructor.class)
        .setParameter("data", id);

    Instructor instructor = query.getSingleResult();

    return instructor;
  }

  @Override
  @Transactional
  public void updateInstructor(Instructor instructor) {
    entityManager.merge(instructor);
  }

  @Override
  public Course findCourseById(int id) {
    Course course = entityManager.find(Course.class, id);

    return course;
  }

  @Override
  @Transactional
  public void updateCourse(Course course) {
    entityManager.merge(course);
  }
}
