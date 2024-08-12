package com.danmou.beginner.repository;

import com.danmou.beginner.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserRepositoryImpl implements UserRepository {

  private EntityManager entityManager;

  public UserRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public User findByUsername(String username) {
    TypedQuery<User> query = entityManager
        .createQuery("FROM User WHERE userName=:username and enable=true", User.class)
        .setParameter("username", username);

    try {
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public void save(User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

}
