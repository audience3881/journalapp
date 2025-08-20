package com.example.journalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.journalapp.entity.JournalEntry;
import com.example.journalapp.entity.User;
import com.example.journalapp.repo.JournalEntryRepo;

import lombok.extern.slf4j.Slf4j;

@Component()
@Slf4j
public class JournalEntryService {
  private JournalEntryRepo journalEntryRepo;
  private UserService userService;

  // inject depen using constr injection
  @Autowired
  JournalEntryService(JournalEntryRepo a1, UserService a2) {
    this.journalEntryRepo = a1;
    this.userService = a2;
  }

  public void create(JournalEntry a1) {
    this.journalEntryRepo.save(a1);
  }

  public List<JournalEntry> findAll() {
    return this.journalEntryRepo.findAll();
  }

  public Optional<JournalEntry> findById(ObjectId a1) {
    return this.journalEntryRepo.findById(a1);
  }

  public void deleteById(ObjectId a1) {
    this.journalEntryRepo.deleteById(a1);
  }

  /*
   * transactional context will be created which will contain all the related db
   * oprs and will be treated as single opr
   */
  @Transactional
  public void create(JournalEntry a1, String a2) {
    // check if user passed in url exist in db
    User userInDb = this.userService.findByUsername(a2);
    a1.setDate(LocalDateTime.now());
    // add the entries in journal_entries coll,1st action is successful
    JournalEntry saved1 = this.journalEntryRepo.save(a1);
    // entries added in doc in user coll
    userInDb.getJournalEntries().add(saved1);
    /*
     * we have given @NonNull in User class so jvm will throw NE and below code wont
     * run.2nd action failed so tx will fail
     */
    // userInDb.setUsername(null);

    // saved updated user with journal entries in users coll
    this.userService.create(userInDb);
  }

  @Transactional
  public boolean deleteById(ObjectId a1, String a2) {
    boolean b1 = false;
    try {
      User userInDb = this.userService.findByUsername(a2);
      // remove entry based on id passed by client in the url
      b1 = userInDb.getJournalEntries().removeIf(ele -> ele.getId().equals(a1));
      if (b1) {
        this.userService.create(userInDb);// save updated user details after entry is deleted
        this.journalEntryRepo.deleteById(a1);// delete entry from journal_entries coll
      }
    } catch (Exception e) {
      log.error("error: ", e);
      throw new RuntimeException("error occured while deleting entry: " + e);
    }
    return b1;
  }

}
