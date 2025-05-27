package com.mooky.blog.domain.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.mooky.pet_diary.domain.blog.BlogService;
import com.mooky.pet_diary.domain.blog.dto.BlogWriteDto;
import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.domain.user.UserRepository;
import com.mooky.pet_diary.domain.user.User.SignUpType;

import io.restassured.RestAssured;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

  private final String path = "v1/blog";

  @LocalServerPort
  private Integer port;

  @Autowired
  private BlogService blogService;

  @Autowired
  private UserRepository userRepository;

  static MariaDBContainer<?> mariaDB = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.5"));

  @BeforeAll
  static void beforeAll() {
    mariaDB.start();
  }

  @AfterAll
  static void afterAll() {
    mariaDB.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
    registry.add("spring.datasource.username", mariaDB::getUsername);
    registry.add("spring.datasource.password", mariaDB::getPassword);
  }

  @BeforeEach
  public void setUp() {
    RestAssured.baseURI = "http://localhost:" + this.port;
    this.userRepository.deleteAll();
  }

  @Test
  public void returnBlogDetails() {
    //save user 
    User user = new User.Builder()
        .username("mooky")
        .email("sookim482.dev@gmail.com")
        .signupType(SignUpType.EMAIL)
        .password("password")
        .build();

    User savedUser = this.userRepository.save(user);
    assertThat(savedUser.getId()).isGreaterThan(0);

    // save blog
    // long savedBlogId = this.blogService
    //     .saveBlogAndReturnBlogDetails(new BlogReq("blog title", "some content", savedUser.getId()))
    //     .getId();
    // assertThat(savedBlogId).isGreaterThan(0);

    // BlogDetails blog = this.blogService.findBlogAndReturnBlogDetails(savedBlogId);
    // assertThat(blog.getId()).isEqualTo(savedBlogId);
    // assertThat(blog.getUserId()).isEqualTo(savedUser.getId());

    // // get blog details 
    // given()
    //     .get(this.path + "/{blogId}", savedBlogId)
    //     .then()
    //     .statusCode(200)
    //     .body("result", equalTo("success"))
    //     .rootPath("payload")
    //     .body("id", equalTo((int) savedBlogId))
    //     .body("userId", equalTo((int) savedUser.getId()));

  }

  // TODO should get accessToken via header
  @Test
  public void saveBlogAndReturnBlogId() {
    // save user
    User user = new User.Builder()
        .username("mooky")
        .email("sookim482.dev@gmail.com")
        .signupType(SignUpType.EMAIL)
        .password("password")
        .build();

    User savedUser = this.userRepository.save(user);
    assertThat(savedUser.getId()).isGreaterThan(0);
    
    Map<String, Object> reqBody = Map.of("title", "how to use spring boot", "content", """
        This is my blog about how to use Spring Boot.
        Let's begin.
        """, "userId", savedUser.getId());
    BlogWriteDto req = given().params(reqBody)
        .when()
        .post(this.path).as(BlogWriteDto.class);
    
    // save 
    // long savedBlogId = this.blogService.saveBlogAndReturnId(req);
    // assertThat(savedBlogId).isGreaterThan(0);

    //BlogReq req = new BlogReq()
  }
}
/*
 * given().parameters("firstName", "John", "lastName",
 * "Doe").when().post("/greetXML").then().body("greeting.firstName",
 * equalTo("John"));
 * will send a POST request to "/greetXML" with request parameters
 * firstName=John and lastName=Doe and expect that the response body containing
 * JSON or XML firstName equal to John.
 */