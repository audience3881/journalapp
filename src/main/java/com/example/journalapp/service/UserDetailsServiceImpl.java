package com.example.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.journalapp.entity.User;
import com.example.journalapp.repo.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
  private UserRepo userRepo;

  // inject depen using ci
  @Autowired
  UserDetailsServiceImpl(UserRepo a1) {
    this.userRepo = a1;
  }

  /* load user creds from username */
  @Override
  public UserDetails loadUserByUsername(String username) {
    // check if user exist in db
    User userInDb = this.userRepo.findByUsername(username);
    try {
      // if user exist in db then fetch the user creds from db
      if (userInDb != null) {
        return org.springframework.security.core.userdetails.User.builder().username(userInDb.getUsername())
            .password(userInDb.getPassword()).roles(userInDb.getRoles().toArray(new String[0])).build();
      }
    } catch (UsernameNotFoundException e) {
      throw new UsernameNotFoundException("user not found with username: " + username);
    }
    return null;
  }
}