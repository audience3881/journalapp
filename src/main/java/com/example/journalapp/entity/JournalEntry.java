package com.example.journalapp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

//create pojo:plain old java obj
/*@Document means given class is mapped to mongodb coll and each ci is mapped to mongodb doc*/
@Document(collection = "journal_entries") // coll name
@Data
@NoArgsConstructor
public class JournalEntry {
  /*
   * id field is mapped to _id field of mongodb doc and _id is generated
   * automatically when doc is created so if we give id will use that id else
   * mongodb will generate id,if we add the new data to same id then data will be
   * updated and no new data will be created
   */
  @Id
  private ObjectId id;// using dt of _id field of mongodb doc
  @NonNull
  private String title;
  private String content;
  private LocalDateTime date;

}
