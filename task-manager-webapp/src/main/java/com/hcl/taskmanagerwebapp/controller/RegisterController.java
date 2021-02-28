package com.hcl.taskmanagerwebapp.controller;

import com.hcl.taskmanagerwebapp.model.User;
import com.hcl.taskmanagerwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
@RequestMapping("/register")
@Controller
public class RegisterController {

  @Autowired
  private UserService userService;

  @GetMapping
  public String showForm(Model model) {
    model.addAttribute("accountInput", new User());
    return "thymeleaf/register";
  }

  @PostMapping
  public String checkUserInfo(@Valid @ModelAttribute("accountInput") User accountInput, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
    if (bindingResult.hasErrors()) {
      if (userService.usernameExists(accountInput.getUsername())) {
        model.addAttribute("usernameAlert", "Username already taken!");
      }
      return "thymeleaf/register";
    }
    if (userService.usernameExists(accountInput.getUsername())) {
      model.addAttribute("usernameAlert", "Username already taken!");
      return "thymeleaf/register";
    }

    userService.addUser(accountInput);

    attributes.addFlashAttribute("registerAlert", "Registration Successful!");
    return "redirect:/login";
  }

}
