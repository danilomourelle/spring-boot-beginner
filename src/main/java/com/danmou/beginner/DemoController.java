package com.danmou.beginner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danmou.beginner.common.Coach;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DemoController {
  private Coach myCoach;
  private Coach anotherCoach;

  @Autowired
  public DemoController(@Qualifier("tennisCoach") Coach coach, @Qualifier("tennisCoach") Coach anotherCoach) {
    this.myCoach = coach;
    this.anotherCoach = anotherCoach;
  }

  @GetMapping("/daily-workout")
  public String getDailyWorkout() {
    return myCoach.getDailyWorkout();
  }

  @GetMapping("/check-scope")
  public String checkCoachesInstances() {
      return "Comparison is " + (myCoach == anotherCoach);
  }
}
