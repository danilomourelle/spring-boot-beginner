package com.danmou.beginner.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(20)
public class Logging {
  @Before("com.danmou.beginner.aspect.Expressions.beforePackageNoGetterSetter()")
  public void someAdvice() {
    System.out.println("======> Logging advice");
  }
}
