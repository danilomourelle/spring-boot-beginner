package com.danmou.beginner.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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
    System.out.println("   " + signature);

    Object[] args = joinPoint.getArgs();
    for (Object argument : args) {
      System.out.println(argument);
      if (argument instanceof Account) {
        Account account = (Account) argument;
        System.out.println(account.getUsername());
      }
    }
  }

  @AfterReturning(pointcut = "execution(* com.danmou.beginner.dao.AccountDAO.findAccounts())", returning = "result")
  public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) {
    System.out.println("======> Logging advice - After Returning");

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    System.out.println("   " + signature);

    System.out.println("   " + result);

    for (Account account : result) {
      account.setUsername(account.getUsername().toUpperCase());
    }
  }
  
  @AfterThrowing(pointcut = "execution(* com.danmou.beginner.dao.AccountDAO.findAccountByUsername())", throwing = "error")
  public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, Throwable error) {
    System.out.println("======> Logging advice - After Throwing");

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    System.out.println("   " + signature);

    System.out.println("   " + error);
  }
}
