package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;

import com.danmou.beginner.entity.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

  private String foo;
  private String bar;

  public String getFoo() {
    System.out.println("Getter on method getFoo");
    return foo;
  }
  
  public void setFoo(String foo) {
    System.out.println("Setter on method getFoo");
    this.foo = foo;
  }

  public String getBar() {
    System.out.println("Getter on method getBar");
    return bar;
  }
  
  public void setBar(String bar) {
    System.out.println("Setter on method getBar");
    this.bar = bar;
  }

  @Override
  public void addAccount() {
    System.out.println("Method signature: public void AccountDAO.addAccount()");
  }

  @Override
  public void addAccount(Account account) {
    System.out.println("Method signature: public void AccountDAO.addAccount(Account account)");
  }
}
