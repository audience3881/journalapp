package com.example.journalapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.journalapp.entity.User;
import com.example.journalapp.repo.UserRepo;

//means spring will start app-context so app-context can store bean inside it
@SpringBootTest
class UserServiceTests {
  private UserRepo userRepo;
  private UserService userService;

  @Autowired
  UserServiceTests(UserRepo a1, UserService a2) {
    this.userRepo = a1;
    this.userService = a2;
  }

  // method is a test method
  @Test
  /*
   * means test method is disabled and will not be executed,we can also provide
   * reason why test method is diabled.when applied on class
   * all the test methods within the class will be disabled
   */
  @Disabled
  public void testFindByUsername() {
    Integer i1 = 2 + 2;
    assertEquals(4, i1);
    assertTrue(1 < 2);
    assertNotNull(this.userRepo.findByUsername("alice"));
    // check if user exist in db
    User userInDb = this.userRepo.findByUsername("dan");
    assertTrue(userInDb.getJournalEntries().isEmpty());
  }

  // method is a parameterized test method and we must pass atleast 1 par
  @ParameterizedTest
  @Disabled
  @CsvSource({ "1,2,3", "1,3,7", "3,3,6" })
  public void test1(int a1, int a2, int a3) {
    assertEquals(a3, a1 + a2, "failed for: " + (a1 + a2));
  }

  @ParameterizedTest
  @Disabled
  // @CsvSource({ "alice", "dan", "name10" })
  /*
   * we can use either @ValueSource or @CsvSource incase of parameterized test
   * methods
   */
  @ValueSource(strings = { "alice", "dan", "name10" })
  public void test2(String username) {
    User userInDb = this.userRepo.findByUsername(username);
    assertNotNull(userInDb, () -> username);// in 2nd arg we pass supplier
  };

  @ParameterizedTest
  @Disabled
  // we can give custom source using ArgumentSource
  @ArgumentsSource(ArgumentsProvider1.class)
  public void test3(User user) {
    boolean b1 = this.userService.create1(user);
    assertTrue(b1);
  }

  /*
   * means annotated method should be executed before each
   * 
   * @Test(),@Parameterized() test method in current test class
   */
  // @BeforeEach()
  void k2() {
  }

  /* means annotated method should be executed before all test methods */
  // @BeforeAll()
  void k3() {
  }

  /*
   * annotated method should be executed after each test method in current test
   * class
   */
  // @AfterEach()
  void k4() {
  }

  /*
   * annotated method should be executed after all test methods in current test
   * class
   */
  // @AfterAll()
  void k5() {
  }
}
