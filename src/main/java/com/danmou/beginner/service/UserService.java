package com.danmou.beginner.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.danmou.beginner.entity.User;
import com.danmou.beginner.web.UserWeb;

public interface UserService extends UserDetailsService {
  public User findByUsername(String username);
  void save(UserWeb userWeb);
}
