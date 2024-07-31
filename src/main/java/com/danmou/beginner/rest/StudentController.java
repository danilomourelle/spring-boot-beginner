package com.danmou.beginner.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.entity.Student;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class StudentController {

  private List<Student> students = new ArrayList<>();

  @PostConstruct
  public void loadData() {
    students.add(new Student("Poornima", "Patel"));
    students.add(new Student("Mario", "Rossi"));
    students.add(new Student("Mary", "Smith"));
  }

  @GetMapping("/students")
  public List<Student> getStudents() {

    return students;
  }

  @GetMapping("/students/{studentId}")
  public Student getStudents(@PathVariable int studentId) {

    return students.get(studentId);
  }
}
