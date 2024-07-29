package com.danmou.beginner.dao;

import java.util.List;

import com.danmou.beginner.entities.Student;

public interface IStudentDAO {
  void save(Student student);
  Student findById(Integer id);
  List<Student> findAll();
  List<Student> findByLastName(String lastName);
}
