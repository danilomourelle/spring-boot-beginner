package com.danmou.beginner.repository;

import com.danmou.beginner.entity.User;

public interface UserRepository {
  public User findByUsername(String username);
  void save(User user);
}
