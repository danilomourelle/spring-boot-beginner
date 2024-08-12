package com.danmou.beginner.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danmou.beginner.entity.Role;
import com.danmou.beginner.entity.User;
import com.danmou.beginner.repository.RoleRepository;
import com.danmou.beginner.repository.UserRepository;
import com.danmou.beginner.web.UserWeb;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void save(UserWeb userWeb) {
    User user = new User();

    user.setUsername(userWeb.getUsername());
    user.setPassword(passwordEncoder.encode(userWeb.getPassword()));
    user.setFirstName(userWeb.getFirstName());
    user.setLastName(userWeb.getLastName());
    user.setEmail(userWeb.getEmail());
    user.setEnabled(true);

    user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_EMPLOYEE")));

    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }

    Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        authorities);
  }

  private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    for (Role tempRole : roles) {
      SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
      authorities.add(tempAuthority);
    }

    return authorities;
  }

}
