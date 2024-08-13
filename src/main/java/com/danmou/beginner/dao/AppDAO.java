package com.danmou.beginner.dao;

import com.danmou.beginner.entity.Instructor;

public interface AppDAO {
  void save(Instructor instructor);
   Instructor findInstructorById(int id);
}
