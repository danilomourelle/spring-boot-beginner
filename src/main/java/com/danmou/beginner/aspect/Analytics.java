package com.danmou.beginner.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(30)
public class Analytics {
  @Before("com.danmou.beginner.aspect.Expressions.beforePackageNoGetterSetter()")
  public void performAnalytics() {
    System.out.println("======> Analytics advice");
  }
}
