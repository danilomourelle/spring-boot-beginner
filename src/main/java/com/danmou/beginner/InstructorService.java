package com.danmou.beginner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.dao.AppDAO;
import com.danmou.beginner.entity.Course;
import com.danmou.beginner.entity.Instructor;

@Service
public class InstructorService {

  private final AppDAO appDAO;

  public InstructorService(AppDAO appDAO) {
    this.appDAO = appDAO;
  }

  @Transactional
  public Instructor findInstructorWithCourses(int id) {
    Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Associated courses: " + instructor.getCourses());


    // Print the type of the courses field before accessing it
    System.out.println("Courses field type before accessing: " + instructor.getCourses().getClass().getName());

    // Access the courses to initialize the lazy-loaded collection
    instructor.getCourses().add(new Course("ForkJoinTask"));

    // Print the type of the courses field after accessing it
    System.out.println("Courses field type after accessing: " + instructor.getCourses().getClass().getName());

    return instructor;
  }
}