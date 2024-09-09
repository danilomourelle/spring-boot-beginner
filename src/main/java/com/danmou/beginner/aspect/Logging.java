package com.danmou.beginner.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
  @Pointcut("execution(* com.danmou.beginner.dao.*.*(..))")
  private void forDaoPackage() {
  };

  @Before("forDaoPackage()")
  public void beforeAddAccountAdvice() {
    System.out.println("==========> Executing Before advice on using PointCut");
  }

  @After("forDaoPackage()")
  public void afterAllAdvices() {
    System.out.println("==========> Executing After advice on using PointCut");
  }
}
