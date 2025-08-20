package com.example.journalapp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.JournalEntry;
import com.example.journalapp.service.JournalEntryService;

@RestController
@RequestMapping("j1")
public class JournalEntryControllerV2 {
  private JournalEntryService journalEntryService;

  // inject depen using constr injection
  @Autowired
  JournalEntryControllerV2(JournalEntryService a1) {
    this.journalEntryService = a1;
  }

  @GetMapping()
  public ResponseEntity<List<JournalEntry>> findAll() {
    List<JournalEntry> entries = this.journalEntryService.findAll();
    if (entries != null && !entries.isEmpty())
      return new ResponseEntity<>(entries, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping()
  public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry a1) {
    try {
      a1.setDate(LocalDateTime.now());
      this.journalEntryService.create(a1);
      return new ResponseEntity<>(a1, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("id/{myId}")
  public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId myId) {
    /*
     * orElse() will return the val if val is present within the optional else
     * return the specified val
     */
    // return this.journalEntryService.findById(myId).orElse(null);
    Optional<JournalEntry> opt1 = this.journalEntryService.findById(myId);
    if (opt1.isPresent()) {
      return new ResponseEntity<>(opt1.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("id/{myId}")
  public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry a1) {
    // find entry by id and update that entry
    JournalEntry old = this.journalEntryService.findById(myId).orElse(null);
    if (old != null) {
      /*
       * if updated entry is not null and not empty then update the old entry with
       * new val
       */
      old.setTitle(a1.getTitle() != null && !a1.getTitle().equals("") ? a1.getTitle() : old.getTitle());
      old.setContent(a1.getContent() != null && !a1.getContent().equals("") ? a1.getContent() : old.getContent());
      this.journalEntryService.create(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    /*
     * if old entry is null then save the old entry and return it and if it is not
     * null then update the old entry and save it
     */
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  // will use wildcard as there is no res-body when server return api-res
  public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
    this.journalEntryService.deleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
/*
 * controller will call service,service will call repo,repo interate with
 * db.controller layer contains endpoints,service layer contains all the
 * business logic
 */