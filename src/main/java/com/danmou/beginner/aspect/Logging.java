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

  @Pointcut("execution(* com.danmou.beginner.dao.*.get*(..))")
  private void forGetterMethods() {
  };

  @Pointcut("execution(* com.danmou.beginner.dao.*.set*(..))")
  private void forSetterMethods() {
  };

  @Pointcut("forDaoPackage() && !forGetterMethods() && !forSetterMethods()")
  public void beforePackageNoGetterSetter() {
  };

  @Before("forDaoPackage()")
  public void beforeAddAccountAdvice() {
    System.out.println("==========> Executing Before advice on using PointCut all methods");
  }

  @After("forDaoPackage()")
  public void afterAllAdvices() {
    System.out.println("==========> Executing After advice on re using PointCut all methods");
  }

  @Before("beforePackageNoGetterSetter()")
  public void someAdvice() {
    System.out.println("==========> Executing Before advice on using PointCut no getter no setter");
  }
}
