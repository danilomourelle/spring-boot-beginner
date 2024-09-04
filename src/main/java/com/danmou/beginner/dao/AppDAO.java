package com.danmou.beginner.dao;

import java.util.List;

import com.danmou.beginner.entity.Course;
import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;

public interface AppDAO {
  void save(Instructor instructor);

  Instructor findInstructorById(int id);

  void deleteInstructorById(int id);

  InstructorDetail findInstructorDetailById(int id);

  void deleteInstructorDetailById(int id);

  List<Course> findCoursesByInstructorId(int id);

  Instructor findInstructorByIdJoinFetch(int id);

  void updateInstructor(Instructor instructor);

  Course findCourseById(int id);

  void updateCourse(Course course);

  void deleteCourseById(int id);

  void saveCourse(Course course);

  Course findCourseAndReviewsByCourseId(int id);
}
