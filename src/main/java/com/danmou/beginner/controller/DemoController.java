package com.danmou.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/leaders")
  public String leaderPage() {
    return "leaders";
  }
}
