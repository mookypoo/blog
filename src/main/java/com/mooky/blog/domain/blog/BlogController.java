package com.mooky.blog.domain.blog;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.blog.vo.BlogDetails;
import com.mooky.blog.domain.blog.vo.BlogReq;
import com.mooky.blog.global.ApiResponse;

import jakarta.validation.Valid;
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
    log.info("getting blog");
    BlogDetails blog = this.blogService.findBlogAndReturnBlogDetails(blogId);
    return ApiResponse.ok(blog);
  }

  @PostMapping
  public ApiResponse createBlog(@Valid @RequestBody BlogReq blogReq) {
    log.info("saving blog");
    BlogDetails blog = this.blogService.saveBlogAndReturnBlogDetails(blogReq);
    return ApiResponse.ok(blog);
  }
  
}
