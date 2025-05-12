package com.mooky.blog.domain.blog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.blog.constraints.SaveBlog;
import com.mooky.blog.domain.blog.vo.BlogDetails;
import com.mooky.blog.domain.blog.vo.BlogReq;
import com.mooky.blog.global.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) 
@RequestMapping("${mooky.endpoint}/v1/blog")
public class BlogController {
  
  final private BlogService blogService;
  
  @GetMapping("/{blogId}")
  public ApiResponse getBlog(@PathVariable("blogId") int blogId) {
    BlogDetails blog = this.blogService.findBlogAndReturnBlogDetails(Integer.toUnsignedLong(blogId));
    return ApiResponse.ok(blog);
  }

  @PostMapping
  public ApiResponse createBlog(@Valid @RequestBody BlogReq blogReq) {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    validator.validate(blogReq, SaveBlog.class);
    BlogDetails blog = this.blogService.saveBlogAndReturnBlogDetails(blogReq);
    return ApiResponse.ok(blog);
  }

  @PatchMapping("/{blogId}")
  public ApiResponse editBlog(@Valid @RequestBody BlogReq blogReq, @PathVariable("blogId") int blogId) {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    validator.validate(blogReq, SaveBlog.class);
    BlogDetails blog = this.blogService.editBlogAndReturnBlogDetails(Integer.toUnsignedLong(blogId), blogReq);
    return ApiResponse.ok(blog);
  }
  
}
