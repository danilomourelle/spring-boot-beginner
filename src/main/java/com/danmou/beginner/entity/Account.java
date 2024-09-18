package com.danmou.beginner.entity;

public class Account {
  private String username;
  private String level;
  
  public Account(String username, String level) {
    this.username = username;
    this.level = level;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getLevel() {
    return level;
  }
  public void setLevel(String level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return "Account [username=" + username + ", level=" + level + "]";
  }
}
