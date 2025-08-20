package com.example.journalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.journalapp.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
// means bean will be used when specified profile is active
// @Profile("dev")
public class SpringSecurity extends WebSecurityConfigurerAdapter {
  private UserDetailsServiceImpl userDetailsServiceImpl;

  SpringSecurity(UserDetailsServiceImpl a1) {
    this.userDetailsServiceImpl = a1;
  }

  /* HttpSecurity defines which reqs needs to be auth and how to be auth */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /*
     * ** means wildcard and can have single/multiplr paths.csrf is enabled by
     * default in sc and when it is enable spring expect csrf token in req and since
     * we dont have one so we disable csrf.users having admin role can access admin
     * url
     */
    http.authorizeRequests().antMatchers("/j2/**", "/user/**").authenticated().antMatchers("/admin/**").hasRole("admin")
        .anyRequest().permitAll().and().httpBasic()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .csrf().disable();
  }

  /*
   * AuthenticationManagerBuilder will check the user creds with the required
   * creds and when matched user is auth and can access the specific route
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    /* check if the hashed psw in mongodb and psw client provided matches or not */
    auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(encoder());
  }

  @Bean()
  PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}