package com.danmou.beginner.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

  @GetMapping(value = "/")
  public String sayHello() {
    return "Hello Word";
  }

}
