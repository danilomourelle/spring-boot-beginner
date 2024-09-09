package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl implements AccountDAO {

  @Override
  public void addAccount() {
    System.out.println("DOING DATABASE WORK: ADDING AN ACCOUNT");
  }
}
