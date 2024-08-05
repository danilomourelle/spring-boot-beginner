package com.danmou.beginner.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DemoController {

  @GetMapping("/hello")
  public String sayHello(Model theModel) {
      theModel.addAttribute("theDate", LocalDateTime.now());

      return "helloworld";
  }
}
