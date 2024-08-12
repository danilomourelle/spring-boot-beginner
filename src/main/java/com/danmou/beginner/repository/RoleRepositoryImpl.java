package com.danmou.beginner.repository;

import org.springframework.stereotype.Repository;

import com.danmou.beginner.entity.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

  private EntityManager entityManager;

  public RoleRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Role findRoleByName(String roleName) {
    TypedQuery<Role> query = entityManager
        .createQuery("FROM Role WHERE name=:roleName", Role.class)
        .setParameter("roleName", roleName);

    try {
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}
