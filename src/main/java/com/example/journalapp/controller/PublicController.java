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

@RestController
@RequestMapping("/public")
public class PublicController {
  private UserService userService;

  PublicController(UserService a1) {
    this.userService = a1;
  }

  /*
   * map method with a specified endpoint,GetMapping() means when client will make
   * a get req to specified url
   */
  @GetMapping("/health-check")
  public String healthCheck() {
    return "ok-1";
  }

  @PostMapping("/createUser")
  public ResponseEntity<User> create(@RequestBody User user) {
    this.userService.create1(user);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
}
