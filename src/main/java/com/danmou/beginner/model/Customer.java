package com.danmou.beginner.model;

import com.danmou.beginner.validation.CourseCode;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Customer {
  private String firstName;

  @NotNull(message = "required")
  @Size(min = 2, message = "at least two character")
  private String lastName;
  
  @NotNull(message = "required")
  @Min(value = 0, message = "must be greater than or equal to 0")
  @Max(value = 10, message = "must be less than or equal to 10")
  private Integer freePasses;

  @Pattern(regexp = "^\\d{5}-\\d{3}", message = "00000-000 format")
  private String postalCode;

  @CourseCode(value = "LUV", message = "Couse code starts with LUV")
  private String courseCode;

  public Customer() {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getFreePasses() {
    return freePasses;
  }

  public void setFreePasses(Integer freePasses) {
    this.freePasses = freePasses;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }
}
