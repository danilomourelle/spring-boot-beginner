package com.danmou.beginner.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.entity.Student;
import com.danmou.beginner.exceptions.StudentErrorResponse;
import com.danmou.beginner.exceptions.StudentNotFoundException;

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
    if (studentId >= students.size() || studentId <0) {
      throw new StudentNotFoundException("Student id not found - " + studentId);
    }

    return students.get(studentId);
  }

  @ExceptionHandler
  public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException e){
    StudentErrorResponse error = new StudentErrorResponse();
    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setMessage(e.getMessage());
    error.setTimestamp(System.currentTimeMillis());

    return new ResponseEntity<StudentErrorResponse>(error, HttpStatus.NOT_FOUND);
  }
}
