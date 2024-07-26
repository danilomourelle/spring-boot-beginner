package com.danmou.beginner.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TennisCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Hit back-hand for 30 minutes!";
  }
}
