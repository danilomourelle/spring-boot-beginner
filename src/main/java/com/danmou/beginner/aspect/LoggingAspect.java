package com.danmou.beginner.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
  private Logger logger = Logger.getLogger(getClass().getName());

  @Pointcut("execution(* com.danmou.beginner.controller.*.*(..))")
  private void forControllerPackage() {
  }

  @Pointcut("execution(* com.danmou.beginner.repository.*.*(..))")
  private void forRepositoryPackage() {
  }

  @Pointcut("execution(* com.danmou.beginner.service.*.*(..))")
  private void forServicePackage() {
  }

  @Pointcut("forControllerPackage() || forRepositoryPackage() || forServicePackage()")
  private void forAppFlow() {
  }

  @Before("forAppFlow()")
  public void before(JoinPoint joinPoint) {
    String methodExecuted = joinPoint.getSignature().toShortString();
    logger.info("===>> in @Before: calling method " + methodExecuted);

    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      logger.info("===>> argument: " + arg);
    }
  }

  @AfterReturning(pointcut = "forAppFlow()", returning = "result")
  public void afterReturning(JoinPoint joinPoint, Object result) {
    String methodExecuted = joinPoint.getSignature().toShortString();
    logger.info("===>> in @AfterReturning: calling method " + methodExecuted);

    logger.info("===>> result: " + result);
  }
}
