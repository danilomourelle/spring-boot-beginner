package com.danmou.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {
  @GetMapping("/show-form")
  public String showForm() {
      return "form";
  }

  @GetMapping("/process-form")
  public String processForm() {
      return "process";
  }
}
