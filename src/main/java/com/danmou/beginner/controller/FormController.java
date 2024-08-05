package com.danmou.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

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

  @GetMapping("form-two")
  public String letShoutDude(HttpServletRequest request, Model model) {
     String theName = request.getParameter("studentName");
     theName = theName.toUpperCase();
     String result = "Yo! " + theName;
     model.addAttribute("message", result);
     
     return "process2";
  }

  @GetMapping("form-three")
  public String someMethod(@RequestParam("studentName") String theName, Model model) {
     theName = theName.toUpperCase();
     String result = "Dude! " + theName;
     model.addAttribute("message", result);
     
     return "process2";
  }
}