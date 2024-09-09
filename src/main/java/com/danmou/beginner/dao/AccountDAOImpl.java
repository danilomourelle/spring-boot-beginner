package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;

import com.danmou.beginner.entity.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {

  @Override
  public void addAccount() {
    System.out.println("Method signature: public void AccountDAO.addAccount()");
  }

  @Override
  public void addAccount(Account account) {
    System.out.println("Method signature: public void AccountDAO.addAccount(Account account)");
  }
}
