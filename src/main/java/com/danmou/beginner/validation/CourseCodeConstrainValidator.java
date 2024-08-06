package com.danmou.beginner.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseCodeConstrainValidator implements ConstraintValidator<CourseCode, String> {
  private String coursePrefix;

  @Override
  public void initialize(CourseCode constrainAnnotation) {
    coursePrefix = constrainAnnotation.value();
  }

  @Override
  public boolean isValid(String codeReceived, ConstraintValidatorContext context) {

    boolean result = codeReceived != null && codeReceived.startsWith(coursePrefix);

    return result;
  }
}
