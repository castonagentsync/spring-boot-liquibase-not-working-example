package com.castonhilcher.liquibasespringboot;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AbstractIT.DockerPostgresDataSourceInitializer.class)
@Testcontainers
public abstract class AbstractIT {

  static final PostgreSQLContainer<?> POSTGRES_DB_CONTAINER;

  static {
    POSTGRES_DB_CONTAINER = new PostgreSQLContainer<>("postgres:15.5");
    POSTGRES_DB_CONTAINER.start();
  }

  public static class DockerPostgresDataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {

      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + POSTGRES_DB_CONTAINER.getJdbcUrl(),
          "spring.datasource.username=" + POSTGRES_DB_CONTAINER.getUsername(),
          "spring.datasource.password=" + POSTGRES_DB_CONTAINER.getPassword(),
          "spring.jpa.properties.hibernate.physical_naming_strategy=" +
          CamelCaseToUnderscoresNamingStrategy.class.getName()
      );
    }
  }
}
