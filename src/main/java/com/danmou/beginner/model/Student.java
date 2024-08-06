package com.danmou.beginner.model;

import java.util.List;

public class Student {
  private String firstName;
  private String lastName;
  private String country;
  private String gender;
  private String favoriteLanguage;
  private List<String> systemsWithKnowledge;

  public Student() {

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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getFavoriteLanguage() {
    return favoriteLanguage;
  }

  public void setFavoriteLanguage(String language) {
    this.favoriteLanguage = language;
  }

  public List<String> getSystemsWithKnowledge() {
    return systemsWithKnowledge;
  }

  public void setSystemsWithKnowledge(List<String> systemsWithKnowledge) {
    this.systemsWithKnowledge = systemsWithKnowledge;
  }
}
