package com.danmou.beginner.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.danmou.beginner.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private EntityManager entityManager;

  public UserRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public User findByUsername(String username) {
    TypedQuery<User> query = entityManager
        .createQuery("FROM User WHERE username=:username and enabled=true", User.class)
        .setParameter("username", username);

    try {
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  @Transactional
  public void save(User user) {
    // create the user ... finally LOL
		entityManager.merge(user);
  }

}
