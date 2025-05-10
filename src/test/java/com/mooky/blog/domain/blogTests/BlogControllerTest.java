package com.mooky.blog.domain.blogTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mooky.blog.domain.blog.BlogController;
import com.mooky.blog.domain.blog.BlogService;
import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.blog.vo.Blog;
import com.mooky.blog.domain.user.entity.UserEntity;

//@TestMethodOrder()
@WebMvcTest(BlogController.class) // disable full auto-configuration
public class BlogControllerTest {
  
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BlogService blogService;

  @Order(1)
  @Test
  public void ifValidBlog_saveBlogDetails() throws Exception {

  }

  @Order(2)
  @DisplayName("Get blog details by blog id")
  @Test
  public void ifValidBlogId_returnBlogDetails() throws Exception {
    long id = 1;
    String title = "My First Blog";
    String content = "Hello";
    UserEntity user = UserEntity.builder().id(1).username("mooky").build();
    BlogEntity blogEntity = BlogEntity.builder()
      .id(id)
      .title(title)
      .content(content)
      //.user(user)
      .build();
    when(blogService.findBlog(1)).thenReturn(new Blog(blogEntity));
    mockMvc.perform(get("/v1/blog/1"))
      .andDo(print())
      .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("""
            {
              result: "success",
              payload: {
                id: %d,
                title: "%s",
                content: "%s",
                username: "%s",
                userId: %d
              }
            }
            """.formatted(id, title, content, user.getUsername(), user.getId())));
      ;
    
  }
}
