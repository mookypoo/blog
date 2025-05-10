package com.mooky.blog.domain.blogTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.mooky.blog.domain.blog.BlogRepository;
import com.mooky.blog.domain.blog.entity.BlogCreaterEntity;
import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.user.UserRepository;
import com.mooky.blog.domain.user.entity.UserEntity;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;



@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class BlogRepositoryTest {

  //@MockitoBean // assertion error - savedBlog below is null --> 실제 로직 수행 X, 원하는 대로 동작 설정 
  @Autowired // blog is not null --> 실제 객체 사용
  private BlogRepository blogRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestEntityManager entityManager;

  private long savedBlogId;

  @BeforeAll
  public void saveUserAndBlog() {
    String username = "mooky";
    UserEntity user = UserEntity.builder().username(username).build();
    UserEntity savedUser = userRepository.save(user);
    
    BlogCreaterEntity blogCreater = new BlogCreaterEntity(savedUser);

    BlogEntity blog = BlogEntity.builder()
        .title("My First Blog")
        .content("Hello")
        .user(blogCreater)
        .build();
    BlogEntity savedBlog = blogRepository.save(blog);
    this.savedBlogId = savedBlog.getId();
    assertThat(savedBlog.getId()).isGreaterThan(0);
  }


  @Test
  @Order(1)
  public void blogRepo_saveBlog_returnSavedBlog() {
    Optional<UserEntity> savedUser = userRepository.findById((long) 1);
    assertThat(savedUser.get()).isNotNull();
    
    BlogEntity blog = BlogEntity.builder()
        .title("My Second Blog")
        .content("Hello Hello")
        //.user(savedUser.get())
        .build();
    BlogEntity savedBlog = blogRepository.save(blog);

    assertThat(savedBlog).isNotNull();
    assertThat(savedBlog.getId()).isGreaterThan(0);
   // assertThat(savedBlog.getUser()).isNotNull();
  }

  @Test
  @Order(2)
  public void blogRepo_returnBlogDetails() throws Exception {
    //BlogEntity savedBlog = this.saveUserAndBlog();

    Optional<BlogEntity> blogOpt = this.blogRepository.findById(1);
    assertThat(blogOpt.get()).isNotNull();
    assertThat(blogOpt.get().getContent()).isEqualTo("Hello");
  }

}
