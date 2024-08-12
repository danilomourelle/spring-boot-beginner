package com.danmou.beginner.repository;

import com.danmou.beginner.entity.Role;

public interface RoleRepository {
  public Role findRoleByName(String roleName);
}
