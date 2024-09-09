package com.danmou.beginner.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
  @Before("execution(public void com.danmou.beginner.dao.AccountDAO.addAccount())")
  public void beforeAddAccountAdvice() {
    System.out.println("==========> Executing Before advice on AccountDAO.addAccount()");
  }

  @Before("execution(* add*(com.danmou.beginner.entity.Account))")
  public void beforeAddAccountWithParamAdvice() {
    System.out.println("==========> Executing Before advice on add*(Account)");
  }

  @Before("execution(* add*())")
  public void beforeAddAdvice() {
    System.out.println("==========> Executing Before advice on add*()");
  }

  @Before("execution(public void updateAccount())")
  public void beforeUpdateAccountAdvice() {
    System.out.println("==========> Executing Before advice on updateAccount()");
  }
}
