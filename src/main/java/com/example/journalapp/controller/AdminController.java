package com.example.journalapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.User;
import com.example.journalapp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
  private UserService userService;

  AdminController(UserService a1) {
    this.userService = a1;
  }

  @GetMapping("all-users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> all1 = this.userService.findAll();
    if (all1 != null && !all1.isEmpty())
      return new ResponseEntity<>(all1, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("create-admin-user")
  public void createAdmin(@RequestBody User user) {
    this.userService.createAdmin(user);
  }
}