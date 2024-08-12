package com.danmou.beginner.controller;

import java.util.logging.Logger;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danmou.beginner.entity.User;
import com.danmou.beginner.service.UserService;
import com.danmou.beginner.web.UserWeb;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
  private Logger logger = Logger.getLogger(getClass().getName());

  private UserService userService;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {

    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @GetMapping()
  public String registerPage(Model model) {

    model.addAttribute("userWeb", new UserWeb());

    return "register/registration-form";
  }

  @PostMapping("/process-register")
  public String processRegistrationForm(
      @Valid @ModelAttribute() UserWeb userWeb,
      BindingResult theBindingResult,
      HttpSession session,
      Model model) {

    String username = userWeb.getUsername();
    logger.info("Processing registration form for: " + username);

    // form validation
    if (theBindingResult.hasErrors()) {
      return "register/registration-form";
    }

    // check the database if user already exists
    User existing = userService.findByUsername(username);
    if (existing != null) {
      model.addAttribute("userWeb", new UserWeb());
      model.addAttribute("registrationError", "User name already exists.");

      logger.warning("User name already exists.");
      return "register/registration-form";
    }

    // create user account and store in the databse
    userService.save(userWeb);

    logger.info("Successfully created user: " + username);

    // place user in the web http session for later use
    session.setAttribute("user", userWeb);

    return "register/registration-confirmation";
  }
}
