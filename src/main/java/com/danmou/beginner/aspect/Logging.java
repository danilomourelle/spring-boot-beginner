package com.danmou.beginner.aspect;

// import java.util.List;

// import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.After;
// import org.aspectj.lang.annotation.AfterReturning;
// import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.danmou.beginner.entity.Account;

@Aspect
@Component
@Order(20)
public class Logging {
  /*
   * @Before(
   * "com.danmou.beginner.aspect.Expressions.beforePackageNoGetterSetter()")
   * public void someAdvice(JoinPoint joinPoint) {
   * System.out.println("======> Logging advice - Before");
   * 
   * MethodSignature signature = (MethodSignature) joinPoint.getSignature();
   * System.out.println("   " + signature);
   * 
   * Object[] args = joinPoint.getArgs();
   * for (Object argument : args) {
   * System.out.println(argument);
   * if (argument instanceof Account) {
   * Account account = (Account) argument;
   * System.out.println(account.getUsername());
   * }
   * }
   * }
   * 
   * @AfterReturning(pointcut =
   * "execution(* com.danmou.beginner.dao.AccountDAO.findAccounts())", returning =
   * "result")
   * public void afterReturningFindAccountsAdvice(JoinPoint joinPoint,
   * List<Account> result) {
   * System.out.println("======> Logging advice - After Returning");
   * 
   * MethodSignature signature = (MethodSignature) joinPoint.getSignature();
   * System.out.println("   " + signature);
   * 
   * System.out.println("   " + result);
   * 
   * for (Account account : result) {
   * account.setUsername(account.getUsername().toUpperCase());
   * }
   * }
   * 
   * @AfterThrowing(pointcut =
   * "execution(* com.danmou.beginner.dao.AccountDAO.findAccountByUsername())",
   * throwing = "error")
   * public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, Throwable
   * error) {
   * System.out.println("======> Logging advice - After Throwing");
   * 
   * MethodSignature signature = (MethodSignature) joinPoint.getSignature();
   * System.out.println("   " + signature);
   * 
   * System.out.println("   " + error);
   * }
   * 
   * @After("execution(* com.danmou.beginner.dao.AccountDAO.findAccountByUsername())"
   * )
   * public void afterReturningFindAccountsAdvice(JoinPoint joinPoint) {
   * System.out.println("======> Logging advice - After");
   * 
   * MethodSignature signature = (MethodSignature) joinPoint.getSignature();
   * System.out.println("   " + signature);
   * }
   */

  @Around("execution(* com.danmou.beginner.dao.AccountDAO.*(..))")
  public Object aroundFindAccountAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("======> Logging advice - Around");

    Object[] args = joinPoint.getArgs();
    for (Object argument : args) {
      System.out.println("======> Logging advice - Around: args " + argument);
    }

    Object result = null;
    try {
      result = joinPoint.proceed(args);
      System.out.println("======> Logging advice - Around: response " + result);

      return result;
    } catch (Exception e) {
      System.out.println("======> Logging advice - Around: catch " + e);
      // throw e;
      result = new Account(null, null);
    }

    return result;

  }
}
