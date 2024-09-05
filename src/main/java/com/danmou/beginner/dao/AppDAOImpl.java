package com.danmou.beginner.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.entity.Course;
import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;
import com.danmou.beginner.entity.Student;

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
     * If 'getReference' is used the query do retrieve data is not immediately
     * executed,
     * instead, ORM will keep watching it, and just query when necessary.
     * For example, in this method, it will print "Lazy" message before select
     * instructor,
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

  @Override
  @Transactional
  public void deleteCourseById(int id) {
    Course course = entityManager.getReference(Course.class, id);

    entityManager.remove(course);
  }

  /**
   * Since it has a relation to Review, nad has cascade ALL,
   * it will also save any data that has changed in related reviews
   */
  @Override
  @Transactional
  public void saveCourse(Course course) {
    entityManager.persist(course);
  }

  /**
   * The JOIN FETCH is a JPA syntax and is used to really make a join query.
   * If we just use "JOIN" it will just fetch for first entity and lazily fetch
   * second.
   */
  @Override
  public Course findCourseAndReviewsByCourseId(int id) {
    TypedQuery<Course> query = entityManager
        .createQuery("SELECT c FROM Course c JOIN FETCH c.reviews WHERE c.id = :data", Course.class)
        .setParameter("data", id);

    Course course = query.getSingleResult();

    return course;
  }

  @Override
  public Course findCourseAndStudentsByCourseId(int id) {
    TypedQuery<Course> query = entityManager
        .createQuery("SELECT c FROM Course c JOIN FETCH c.students WHERE c.id = :data", Course.class)
        .setParameter("data", id);

    Course course = query.getSingleResult();

    return course;
  }

  @Override
  public Student findStudentAndCoursesByStudentId(int id) {
    TypedQuery<Student> query = entityManager
        .createQuery("SELECT s FROM Student s JOIN FETCH s.courses WHERE s.id = :data", Student.class)
        .setParameter("data", id);

    Student student = query.getSingleResult();

    return student;
  }

  @Override
  @Transactional
  public void updateStudent(Student student) {
    entityManager.merge(student);
  }

  @Override
  @Transactional
  public void deleteStudentById(int id) {
    Student student = entityManager.getReference(Student.class, id);

    entityManager.remove(student);
  }
}
