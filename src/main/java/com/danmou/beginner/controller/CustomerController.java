package com.danmou.beginner.controller;

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

import com.danmou.beginner.model.Customer;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @GetMapping("/form")
  public String createForm(Model model) {
    model.addAttribute("customer", new Customer());
    return "customer-form";
  }

  @PostMapping("/confirmation")
  public String processForm(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindResult) {
    if (bindResult.hasErrors()) {
      System.out.println(bindResult);
      System.out.println("***" + customer.getLastName() + "***");
      return "customer-form";
    }
    
    System.out.println("***" + customer.getLastName() + "***");

    return "customer-confirmation";
  }
}
