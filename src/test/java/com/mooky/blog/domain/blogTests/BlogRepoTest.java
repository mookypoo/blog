package com.mooky.blog.domain.blogTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.mooky.blog.domain.blog.BlogRepository;
import com.mooky.blog.domain.blog.entity.BlogEntity;

@DataJpaTest
public class BlogRepoTest {
  // var mariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.5"));
  // mariaDB.start();

  // static MariaDBContainer<?> mariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.5"));

  @Autowired
  private BlogRepository blogRepository;

  // @BeforeAll
  // static void startDB() {
  //   mariaDB.start();
  // }

  // @AfterAll
  // static void AfterAll() {
  //   mariaDB.stop();
  // }

  // @DynamicPropertySource
  // static void configureProperties(DynamicPropertyRegistry registry) {
  //   registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
  //   registry.add("spring.datasource.username", mariaDB::getUsername);
  //   registry.add("spring.datasource.password", mariaDB::getPassword);
  // }

  @Test
  public void returnBlogDetails() throws Exception {
    

    Optional<BlogEntity> blogOpt = this.blogRepository.findById(1);
    assertThat(blogOpt.get()).isNotNull();
    assertThat(blogOpt.get().getContent()).isEqualTo("Hello");
  }
}
