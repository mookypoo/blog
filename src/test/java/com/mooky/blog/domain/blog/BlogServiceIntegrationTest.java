package com.mooky.blog.domain.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mooky.blog.domain.blog.dto.BlogWriteDto;
import com.mooky.blog.domain.blog.BlogService;
import com.mooky.blog.domain.blog.dto.BlogDetailsDto;
import com.mooky.blog.domain.blog.entity.Blog;
import com.mooky.blog.domain.blog.repository.BlogRepository;
import com.mooky.pet_diary.domain.user.User;
import com.mooky.pet_diary.domain.user.UserRepository;
import com.mooky.pet_diary.domain.user.User.SignUpType;
import com.mooky.pet_diary.global.exception.ApiException.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class BlogServiceIntegrationTest {

    private @Autowired BlogService blogService;
    private @Autowired BlogRepository blogRepository;
    private @Autowired UserRepository userRepository;
    private @Autowired EntityManager entityManager;

    User savedUser;

    @BeforeEach
    private void setUpUser() {
        User user = new User.Builder()
                .username("test user")
                .email("test@gmail.com")
                .signupType(SignUpType.EMAIL)
                .build();
        this.savedUser = this.userRepository.save(user);
    }

    @Test
    public void shouldReturnBlogDetails() {
        Blog blog = Blog.builder()
                .title("test title")
                .content("test content")
                .authorId(this.savedUser.getId()) 
                .build();
        Blog savedBlog = this.blogRepository.save(blog);

        // Clear persistence context to ensure we're getting fresh data from DB
        // without clear(), findBlogAndReturnBlogDetails returns the savedBlog entity, which does not have BlogCreator value set
        this.entityManager.clear();

        BlogDetailsDto blogResponse = this.blogService.findBlogAndReturnBlogDetails(savedBlog.getId());

        assertThat(blogResponse).isNotNull();
        assertThat(blogResponse.getTitle()).isEqualTo("test title");
        assertThat(blogResponse.getContent()).isEqualTo("test content");
        assertThat(blogResponse.getAuthor().getAuthorId()).isEqualTo(this.savedUser.getId());
        assertThat(blogResponse.getAuthor().getUsername()).isEqualTo("test user");
    }
    
    @Test
    public void shouldThrowNotFoundExceptionWhenBlogNotFound() {
        assertThatThrownBy(() -> this.blogService.findBlogAndReturnBlogDetails(999999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("blog_not_found");
    }
  
    @Test
    public void shouldHandleNullAuthorCorrectly() {
        BlogWriteDto blogReq = BlogWriteDto.builder()
                .title("test title")
                .content("test content")
                .userId(999999L).build();

        assertThatThrownBy(() -> this.blogService.saveBlogAndReturnBlogDetails(blogReq))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("user_not_found");
    }

    @Test
    public void shouldSaveBlogAndReturnBlogDetails() {
        BlogWriteDto blogReq = BlogWriteDto.builder()
                .title("test title")
                .content("test content")
                .userId(this.savedUser.getId()).build();
        BlogDetailsDto blogResponse = this.blogService.saveBlogAndReturnBlogDetails(blogReq);

        assertThat(blogResponse).isNotNull();
        assertThat(blogResponse.getTitle()).isEqualTo("test title");
        assertThat(blogResponse.getContent()).isEqualTo("test content");
        assertThat(blogResponse.getAuthor().getAuthorId()).isEqualTo(this.savedUser.getId());
        assertThat(blogResponse.getAuthor().getUsername()).isEqualTo("test user");
    }
    
    // TODO edit blog 
}
