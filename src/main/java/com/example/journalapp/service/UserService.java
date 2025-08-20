package com.example.journalapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.journalapp.entity.User;
import com.example.journalapp.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Component
/*
 * will inject Logger instance and access via log,no need to create Logger
 * instance
 */
@Slf4j
public class UserService {
  private UserRepo userRepo;
  private PasswordEncoder encoder;
  /*
   * create Logger instance.to prevent re-assignment we make it
   * final.LoggerFactory is a utility class and each class is associated with
   * specific class
   */
  // private static final Logger logger =
  // LoggerFactory.getLogger(UserService.class);

  // inject depen using ci
  @Autowired
  UserService(UserRepo a1, PasswordEncoder a2) {
    this.userRepo = a1;
    this.encoder = a2;
  }

  public List<User> findAll() {
    return this.userRepo.findAll();
  }

  public void create(User a1) {
    this.userRepo.save(a1);
  }

  public boolean create1(User user) {
    try {
      user.setPassword(encoder.encode(user.getPassword()));
      user.setRoles(Arrays.asList("user"));
      this.userRepo.save(user);
      return true;
    } catch (Exception e) {
      // we give placeholder using {}
      log.info("info");
      log.warn("warn");
      log.debug("debug");
      log.error("error: {}", user.getUsername(), e.getMessage());
      log.trace("trace");
      return false;
    }
  }

  public void createAdmin(User user) {
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList("user", "admin"));
    this.userRepo.save(user);
  }

  public Optional<User> findById(ObjectId a1) {
    return this.userRepo.findById(a1);
  }

  public void deleteById(ObjectId a1) {
    this.userRepo.deleteById(a1);
  }

  public User findByUsername(String userName) {
    return this.userRepo.findByUsername(userName);
  }

  public void deleteByUsername(String username) {
    this.userRepo.deleteByUsername(username);
  }
}