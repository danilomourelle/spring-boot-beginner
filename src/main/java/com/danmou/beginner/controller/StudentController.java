package com.danmou.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.danmou.beginner.model.Student;

@Controller
public class StudentController {

  @GetMapping("/student-form")
  public String showForm(Model model) {
    Student student = new Student();
    model.addAttribute("student", student);

    return "student-form";
  }

  @PostMapping("/process-form")
  public String processForm(@ModelAttribute("student") Student student) {

    System.out.println("Student: " + student.getFirstName() + " " + student.getLastName());

    return "student-confirmation";
  }

}
