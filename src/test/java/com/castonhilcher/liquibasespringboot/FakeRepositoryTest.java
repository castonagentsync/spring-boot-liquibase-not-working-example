package com.castonhilcher.liquibasespringboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FakeRepositoryTest extends AbstractIT {
  @Autowired
  private PersonRepository repository;

  @Test
  void fakeTest() {
    long count = repository.count();
    Assertions.assertEquals(0L, count);
  }
}
