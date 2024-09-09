package com.danmou.beginner.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(10)
public class Security {
  @Before("com.danmou.beginner.aspect.Expressions.beforePackageNoGetterSetter()")
  public void secureMethod() {
    System.out.println("======> Secure advice");
  }
}
