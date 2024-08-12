package com.danmou.beginner.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.danmou.beginner.entity.User;
import com.danmou.beginner.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private UserService userService;

  public CustomAuthenticationSuccessHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String username = authentication.getName();
    System.out.println("username=" + username);

    User user = userService.findByUsername(username);

    HttpSession session = request.getSession();
    session.setAttribute("user", user);

    response.sendRedirect(request.getContextPath() + "/");
  }
}