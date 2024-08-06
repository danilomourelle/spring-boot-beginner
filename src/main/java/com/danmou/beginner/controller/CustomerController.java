package com.danmou.beginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.danmou.beginner.model.Customer;

import jakarta.validation.Valid;

@Controller
public class CustomerController {

  @GetMapping("/customer-form")
  public String createForm(Model model) {
    model.addAttribute("customer", new Customer());
    return "customer-form";
  }

  @PostMapping("/process-form")
  public String processForm(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindResult) {
    if (bindResult.hasErrors()) {
      System.out.println(bindResult);
      return "customer-form";
    }

    System.out.println("Foo" + customer.getLastName());

    return "customer-confirmation";
  }
}
