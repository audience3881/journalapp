package com.example.journalapp.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.example.journalapp.entity.User;

public class ArgumentsProvider1 implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    return Stream.of(Arguments.of(User.builder().username("name4").password("random4").build()),
        Arguments.of(User.builder().username("alice1").password("alice1").build()));
  }
}