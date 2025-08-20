package com.example.journalapp.controller;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.JournalEntry;

@RestController
/* @RequstMapping() will add mapping on the entire class */
@RequestMapping("journalEntry")
class JournalEntryController {
  private Map<ObjectId, JournalEntry> hm1 = new HashMap<>();

  /*
   * all the method in controller class must be public so they can be accessed and
   * invoked by sf them,each method must have unique endpoint
   */
  @GetMapping()
  public List<JournalEntry> getAll() {
    return new ArrayList<>(hm1.values());
  }

  /*
   * selecting raw and json in req body of post req indicates req body will
   * contain data in json format allowing server to parse and process incoming
   * data,this ensures that data is sent and received in structured manner
   */
  @PostMapping()
  /*
   * @RequestBody() means client will send the data as payload to req-body and
   * spring will take the data from req-body and turns it into java obj
   */
  public boolean create(@RequestBody JournalEntry a1) {
    hm1.put(a1.getId(), a1);
    return true;
  }

  /* method is mapped to specified endpoint */
  @GetMapping("id/{myId}")
  public JournalEntry findById(@PathVariable String myId) {
    return hm1.get(myId);
  }

  // delete
  @DeleteMapping("id/{myId}")
  public JournalEntry deleteById(@PathVariable String myId) {
    // will remove the mapping for the specified key and returns it
    return hm1.remove(myId);
  }

  // update
  @PutMapping("id/{myId}")
  public void updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry a1) {
    hm1.put(myId, a1);
  }
}
/*
 * here we were using in-memory caching for storing data so when server restarts
 * data is deleted,to implem persistent storage we use db like mongodb
 */