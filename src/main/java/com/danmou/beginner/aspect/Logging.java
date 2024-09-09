package com.danmou.beginner.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.danmou.beginner.entity.Account;

@Aspect
@Component
@Order(20)
public class Logging {
  @Before("com.danmou.beginner.aspect.Expressions.beforePackageNoGetterSetter()")
  public void someAdvice(JoinPoint joinPoint) {
    System.out.println("======> Logging advice");

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    System.out.println(signature);

    Object[] args = joinPoint.getArgs();
    for (Object argument : args) {
      System.out.println(argument);
      if (argument instanceof Account) {
        Account account = (Account) argument;
        System.out.println(account.getUsername());
      }
    }
  }
}
