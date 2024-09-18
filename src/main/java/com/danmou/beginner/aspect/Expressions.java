package com.danmou.beginner.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // optional if there is only pointcuts
public class Expressions {
  @Pointcut("execution(* com.danmou.beginner.dao.*.*(..))")
  public void forDaoPackage() {
  };

  @Pointcut("execution(* com.danmou.beginner.dao.*.get*(..))")
  public void forGetterMethods() {
  };

  @Pointcut("execution(* com.danmou.beginner.dao.*.set*(..))")
  public void forSetterMethods() {
  };

  @Pointcut("forDaoPackage() && !forGetterMethods() && !forSetterMethods()")
  public void beforePackageNoGetterSetter() {
  };
}


