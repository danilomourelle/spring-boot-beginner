package com.danmou.beginner.common;

import org.springframework.stereotype.Component;

@Component
public class BaseballCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Training batting for 45 minutes";
  }
}
