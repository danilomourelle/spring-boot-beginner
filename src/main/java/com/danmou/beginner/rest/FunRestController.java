package com.danmou.beginner.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

  @Value("${team.coach}")
  private String teamCoach;
  @Value("${team.manager}")
  private String teamManager;
  @Value("${team.owner}")
  private String teamOwner;

  @GetMapping(value = "/hello-world")
  public String sayHello() {
    return "Hello Word " + teamCoach + " " + teamManager + " " + teamOwner;
  }

}
