package com.example.journalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.User;
import com.example.journalapp.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

  private UserService userService;
  private final PasswordEncoder encoder;

  @Autowired
  UserController(UserService a1, PasswordEncoder a2) {
    this.userService = a1;
    this.encoder = a2;
  }

  @GetMapping()
  public List<User> findAll() {
    return this.userService.findAll();
  }

  /* as we have done indexing in User class so we can find user by user */
  @PutMapping()
  public ResponseEntity<User> updateByUserName(@RequestBody User user) {
    // if user is auth then it will fetch auth creds
    Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
    String username = auth1.getName();
    /*
     * check if user creds provided by client when client loggingin matches with the
     * user creds exist in db
     */
    User userInDb = this.userService.findByUsername(username);
    /*
     * we dont have to check if user exist in db or not since auth user's creds are
     * stored in db
     */
    // update the user details
    userInDb.setUsername(user.getUsername());
    userInDb.setPassword(encoder.encode(user.getPassword()));// update the psw and hash it
    // save the updated user details in db
    this.userService.create(userInDb);
    return new ResponseEntity<>(userInDb, HttpStatus.OK);
  }

  @DeleteMapping()
  public ResponseEntity<?> deleteByUsername(String username) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    this.userService.deleteByUsername(auth.getName());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
