package com.danmou.beginner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.danmou.beginner.model.Student;

@Controller
public class StudentController {
  @Value("${countries}")
  private List<String> countries;

  @GetMapping("/student-form")
  public String showForm(Model model) {
    Student student = new Student();
    model.addAttribute("student", student);
    model.addAttribute("countries", countries);

    return "student-form";
  }

  @PostMapping("/process-form")
  public String processForm(@ModelAttribute("student") Student student) {

    System.out.println("Student: " + student.getFirstName() + " " + student.getLastName());

    return "student-confirmation";
  }

}
