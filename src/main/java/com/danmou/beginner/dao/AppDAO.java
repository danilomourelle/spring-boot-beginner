package com.danmou.beginner.dao;

import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;

public interface AppDAO {
  void save(Instructor instructor);
   Instructor findInstructorById(int id);
   void deleteInstructorById(int id);
   InstructorDetail findInstructorDetailById(int id);
}
