package com.danmou.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  @GetMapping("/login")
  public String loginPage() {
    return "custom-login";
  }

  @GetMapping("/access-denied")
  public String accessDeniedPage() {
    return "access-denied";
  }
}
