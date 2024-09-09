package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipDAOImpl implements MembershipDAO {

  @Override
  public void addMember() {
    System.out.println("DOING DATABASE WORK: ADDING AN MEMBERSHIP ACCOUNT");
  }
}
