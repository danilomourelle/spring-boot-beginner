package com.danmou.beginner.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentController {

  @GetMapping("/students")
  public List<Student> getStudents() {
    List<Student> students = new ArrayList<>();

    students.add(new Student("Poornima", "Patel"));
    students.add(new Student("Mario", "Rossi"));
    students.add(new Student("Mary", "Smith"));
    
    return students;
  }
}
