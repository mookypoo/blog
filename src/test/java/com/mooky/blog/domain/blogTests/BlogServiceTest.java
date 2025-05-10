package com.mooky.blog.domain.blogTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.mooky.blog.domain.blog.BlogRepository;

@SpringBootTest
public class BlogServiceTest {

  @MockitoBean
  private BlogRepository blogRepository;

  @Test
  @DisplayName("Throw error if blog not found by id")
  public void ifInvalidBlog_throwError() {
   // blogRepository.findById(100)
  }
}
