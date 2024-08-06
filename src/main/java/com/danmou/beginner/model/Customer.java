package com.danmou.beginner.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Customer {
  private String firstName;

  @NotNull(message = "required")
  @Size(min = 2, message = "at least two character")
  private String lastName;

  @Min(value = 0, message = "must be greater than or equal to 0")
  @Max(value = 10, message = "must be less than or equal to 10")
  private Integer freePasses;

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
}
