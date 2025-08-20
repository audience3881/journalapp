package com.example.journalapp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Document(collection = "users")
@Data
@Builder
public class User {
  /*
   * id field is mapped to _id field in mongodb doc and _id field is generated
   * automatically to uniquely identify the doc
   */
  @Id
  private ObjectId id;
  /*
   * unique means each username will be unique
   * NonNull means fields cant be null or empty and jvm will throw NE if field is
   * null
   */
  @Indexed(unique = true)
  @NonNull
  private String username;
  @NonNull
  private String password;
  /*
   * @DBRef means we are creating ref of journal_entries in users coll and will
   * link both colls
   */
  @DBRef
  private List<JournalEntry> journalEntries = new ArrayList<>();
  private List<String> roles;
}
