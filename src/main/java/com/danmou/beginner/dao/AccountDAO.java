package com.danmou.beginner.dao;

import java.util.List;

import com.danmou.beginner.entity.Account;

public interface AccountDAO {
  void addAccount();

  void addAccount(Account account);

  public String getFoo();

  public void setFoo(String foo);

  public String getBar();

  public void setBar(String bar);

  public List<Account> findAccounts();

  public Account findAccountByUsername();
}
