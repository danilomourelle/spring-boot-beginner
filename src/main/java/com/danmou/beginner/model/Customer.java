package com.danmou.beginner.model;

import jakarta.validation.constraints.NotBlank;

public class Customer {
  private String firstName;

  @NotBlank(message = "required")
  // @Size(min = 2, message = "at least two character");
  private String lastName;

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

}
