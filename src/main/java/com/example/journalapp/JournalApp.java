package com.example.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class JournalApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalApp.class, args);
		ConfigurableEnvironment env = context.getEnvironment();
		System.out.println("env: " + env.getActiveProfiles()[0]);
	}
}
