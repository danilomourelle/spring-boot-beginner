package com.danmou.beginner.dao;

import com.danmou.beginner.entity.Account;

public interface AccountDAO {
  void addAccount();

  void addAccount(Account account);

  public String getFoo();

  public void setFoo(String foo);

  public String getBar();

  public void setBar(String bar);

}
