package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipDAOImpl implements MembershipDAO {

  @Override
  public boolean addMember() {
    System.out.println("DOING DATABASE WORK: ADDING AN MEMBERSHIP ACCOUNT");

    return true;
  }
}
