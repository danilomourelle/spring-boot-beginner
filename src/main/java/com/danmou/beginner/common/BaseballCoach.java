package com.danmou.beginner.common;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BaseballCoach implements Coach {

  @Override
  public String getDailyWorkout() {
    return "Training batting for 45 minutes";
  }
}
