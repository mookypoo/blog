package com.mooky.blog.domain.blogTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.mooky.blog.domain.blog.BlogController;
import com.mooky.blog.domain.blog.BlogService;
import com.mooky.blog.domain.blog.entity.BlogCreaterEntity;
import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.blog.vo.BlogReq;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.entity.UserEntity;
import com.mooky.blog.domain.user.entity.UserEntity.SignUpType;

import io.restassured.RestAssured;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

  @LocalServerPort
  private Integer port;

  // @Autowired
  // private MockMvc mockMvc;

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
  }

  @Test
  public void returnBlogDetails() {
    // save user 
    UserEntity user = new UserEntity.Builder()
      .username("Mooky")
      .email("sookim482.dev@gmail.com")
      .signupType(SignUpType.EMAIL)
      .password("password")
      .build();

    UserEntity savedUser = this.userRepository.save(user);
    assertThat(savedUser.getId()).isGreaterThan(0);
    
    // save blog
    long savedBlogId = this.blogService.saveBlogAndReturnId(new BlogReq("blog title", "some content", savedUser.getId()));
    assertThat(savedBlogId).isGreaterThan(0);
    
    // get blog details 
    given()
      .get("/v1/blog/{blogId}", 1)
      .then()
      .statusCode(200)
      .body("result", equalTo("success"))
        ;
  }

  /*
   * writing:
   * 
   * when().
   * get("/item/"+myItem.getItemNumber()+"/buy/"+2).
   * then().
   * statusCode(200);
   * you can write:
   * 
   * given().
   * pathParam("itemNumber", myItem.getItemNumber()).
   * pathParam("amount", 2).
   * when().
   * get("/item/{itemNumber}/buy/{amount}").
   * then().
   * statusCode(200);
   * 
   * given().parameters("firstName", "John", "lastName",
   * "Doe").when().post("/greetXML").then().body("greeting.firstName",
   * equalTo("John"));
   * will send a POST request to "/greetXML" with request parameters
   * firstName=John and lastName=Doe and expect that the response body containing
   * JSON or XML firstName equal to John.
   */
  // @Order(2)
  // @DisplayName("Get blog details by blog id")
  // @Test
  // public void ifValidBlogId_returnBlogDetails() throws Exception {
  //   long id = 1;
  //   String title = "My First Blog";
  //   String content = "Hello";
  //   UserEntity user = UserEntity.builder().id(1).username("mooky").build();
  //   BlogEntity blogEntity = BlogEntity.builder()
  //     .id(id)
  //     .title(title)
  //     .content(content)
  //     //.user(user)
  //     .build();
  //   when(blogService.findBlog(1)).thenReturn(new Blog(blogEntity));
  //   mockMvc.perform(get("/v1/blog/1"))
  //     .andDo(print())
  //     .andExpect(status().isOk())
  //       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
  //       .andExpect(content().json("""
  //           {
  //             result: "success",
  //             payload: {
  //               id: %d,
  //               title: "%s",
  //               content: "%s",
  //               username: "%s",
  //               userId: %d
  //             }
  //           }
  //           """.formatted(id, title, content, user.getUsername(), user.getId())));
  //     ;

  // }
}
