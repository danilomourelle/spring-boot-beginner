package com.danmou.beginner.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipDAOImpl implements MembershipDAO {

  @Override
  public boolean addMember() {
    System.out.println("Method signature: public boolean MembershipDAO.addMember()");

    return true;
  }
}
