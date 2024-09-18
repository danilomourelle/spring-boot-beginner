package com.danmou.beginner.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.danmou.beginner.entity.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

  private String foo;
  private String bar;

  public String getFoo() {
    System.out.println("======> DAO: Getter on method getFoo");
    return foo;
  }

  public void setFoo(String foo) {
    System.out.println("======> DAO: Setter on method getFoo");
    this.foo = foo;
  }

  public String getBar() {
    System.out.println("======> DAO: Getter on method getBar");
    return bar;
  }

  public void setBar(String bar) {
    System.out.println("======> DAO: Setter on method getBar");
    this.bar = bar;
  }

  @Override
  public void addAccount() {
    System.out.println("======> DAO:  public void AccountDAO.addAccount()");
  }

  @Override
  public void addAccount(Account account) {
    System.out.println("======> DAO: public void AccountDAO.addAccount(Account account)");
  }

  @Override
  public List<Account> findAccounts() {
    System.out.println("======> DAO: public void AccountDAO.findAccounts()");
    List<Account> accounts = new ArrayList<>();
    
    accounts.add(new Account("John", "Gold"));
    accounts.add(new Account("Luca", "Silver"));
    accounts.add(new Account("Mary", "Bronze"));
    
    return accounts;
  }
  
  @Override
  public Account findAccountByUsername() {
    System.out.println("======> DAO: public void AccountDAO.findAccountsByUsername()");
    throw new RuntimeException("Buuhhhhh");
  }
}
