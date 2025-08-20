package com.example.journalapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.JournalEntry;
import com.example.journalapp.entity.User;
import com.example.journalapp.service.JournalEntryService;
import com.example.journalapp.service.UserService;

@RestController
@RequestMapping("j2")
public class JournalEntryControllerV3 {
  private JournalEntryService journalEntryService;
  private UserService userService;

  JournalEntryControllerV3(JournalEntryService a1, UserService a2) {
    this.journalEntryService = a1;
    this.userService = a2;
  }

  // get all the entries of specific user
  @GetMapping()
  public ResponseEntity<List<JournalEntry>> findAllJournalEntriesOfUser() {
    // fetch the auth creds once user is auth
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User user = this.userService.findByUsername(username);
    // retrieve all the entries of specific user
    List<JournalEntry> all1 = user.getJournalEntries();
    if (all1 != null && !all1.isEmpty())
      return new ResponseEntity<>(all1, HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  // add entries of specific user
  @PostMapping()
  public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry a1) {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String username = auth.getName();
      this.journalEntryService.create(a1, username);
      return new ResponseEntity<>(a1, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("id/{id}")
  public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
    // fetch the auth creds when user is auth
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User user = this.userService.findByUsername(username);
    List<JournalEntry> entries = user.getJournalEntries().stream().filter(ele -> ele.getId().equals(id))
        .collect(Collectors.toList());
    if (!entries.isEmpty()) {
      Optional<JournalEntry> opt1 = this.journalEntryService.findById(id);
      if (opt1.isPresent())
        return new ResponseEntity<>(opt1.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  // delete entries of specific user
  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean b1 = this.journalEntryService.deleteById(myId, auth.getName());
    if (b1)
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  // update entires of specific user
  @PutMapping("id/{myId}")
  public ResponseEntity<JournalEntry> updateByIdAndUsername(@RequestBody JournalEntry a1, @PathVariable ObjectId myId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User user = this.userService.findByUsername(username);
    List<JournalEntry> entries = user.getJournalEntries().stream().filter(ele -> ele.getId().equals(myId))
        .collect(Collectors.toList());
    if (!entries.isEmpty()) {
      Optional<JournalEntry> opt1 = this.journalEntryService.findById(myId);
      if (opt1.isPresent()) {
        JournalEntry old = opt1.get();
        old.setTitle(a1.getTitle() != null && !a1.getTitle().equals("") ? a1.getTitle() : old.getTitle());
        old.setContent(a1.getContent() != null && !a1.getContent().equals("") ? a1.getContent() : old.getContent());
        this.journalEntryService.create(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
