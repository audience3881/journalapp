package com.example.journalapp.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.example.journalapp.entity.User;
import com.example.journalapp.repo.UserRepo;

@ActiveProfiles("dev")
/*
 * we will not use @SpringBootTest as we are not using app-context but using
 * mocks so we will not use @Autowired to inject depen
 */
class UserDetailsServiceImplTests {
  // means inject all the mock annotated with @Mock as a depen
  @InjectMocks
  private UserDetailsServiceImpl userDetailsServiceImpl;
  /* when we use app-context then we use @MockBean instead of @Mock */
  @Mock
  private UserRepo userRepo;

  /*
   * annotated method should be executed before each test method within given test
   * class
   */
  @BeforeEach()
  @Disabled
  void setUp() {
    /*
     * initialize all the mocks annotated with mockito annotations like
     * 
     * @InjectMocks(),@Mocks(),etc within same class
     */
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @Disabled
  void test1() {
    when(this.userRepo.findByUsername(ArgumentMatchers.anyString()))
        .thenReturn(User.builder().username("ram").password("pass1").roles(Arrays.asList()).build());
    UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername("ram");
    Assertions.assertNotNull(userDetails);
  }
}