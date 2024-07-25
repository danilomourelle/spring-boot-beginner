package com.danmou.beginner.common;

import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Hit back-hand for 30 minutes!";
  }
}
