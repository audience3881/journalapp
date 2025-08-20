package com.example.journalapp.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.journalapp.entity.User;

public interface UserRepo extends MongoRepository<User, ObjectId> {
  // create custom method,only 1st char of entity field must be capital
  User findByUsername(String username);

  void deleteByUsername(String username);
}