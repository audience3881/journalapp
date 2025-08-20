package com.example.journalapp.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.journalapp.entity.JournalEntry;

//in the type arg we pass mapped class and id type
public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
}
/*
 * MongoRepository is an interface provided by spring data mongodb.map class
 * with the collection.at runtime sb will create implem of JournalEntryRepo
 * interface
 */