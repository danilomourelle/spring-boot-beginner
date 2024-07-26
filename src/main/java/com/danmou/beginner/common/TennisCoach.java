package com.danmou.beginner.common;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TennisCoach implements Coach {

  public TennisCoach() {
    System.out.println("In constructor " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Hit back-hand for 30 minutes!";
  }
}
