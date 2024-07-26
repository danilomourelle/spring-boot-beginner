package com.danmou.beginner.common;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class TennisCoach implements Coach {
  
  public TennisCoach() {
    System.out.println("In constructor " + getClass().getSimpleName());
  }

  @PostConstruct
  public void start() {
    System.out.println("Start up " + getClass().getSimpleName());
  }

  @PreDestroy
  public void stop() {
    System.out.println("Stop down " + getClass().getSimpleName());
  }

  @Override
  public String getDailyWorkout() {
    return "Hit back-hand for 30 minutes!";
  }
}
