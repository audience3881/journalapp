package com.example.journalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*it means ioc-container will be configured using jaav config file. we will create bean using @Bean() applied on methods instead of @Component() applied on class*/
@Configuration
/*
 * it applies on main class only and will scan all the methods where
 * 
 * @Transactional is applied
 */
@EnableTransactionManagement
public class TransactionConfig {
  /*
   * as SpringBootApplication does task of 3
   * annotations @Configuration,@EnableAutoConfiguration,@ComponentScan so we can
   * create bean inside this class using @Bean which is applied on methods.
   * MongoDatabaseFactory helps to connect to db
   */
  @Bean()
  public PlatformTransactionManager k1(MongoDatabaseFactory a1) {
    /* in the arg spring will pass SimpleMongoClientDatabaseFactory as its implem */
    return new MongoTransactionManager(a1);
  }
}
/*
 * PlatformTransactionManager is an interface
 * MongoTransactionManager is the implem class of ptm that manages each tx
 * action and if any action fails then all actions will be rollbacked and tx
 * will failed.
 */