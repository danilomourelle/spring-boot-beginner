package com.danmou.beginner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.common.Coach;


@RestController
public class DemoController {
  private Coach myCoach;

  @Autowired
  public DemoController(@Qualifier("aquatic") Coach coach) {
    this.myCoach = coach;
  }

  @GetMapping("/daily-workout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }
}
