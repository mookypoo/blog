package com.mooky.blog.domain.blog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.blog.vo.Blog;
import com.mooky.blog.global.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) 
@RequestMapping("/v1/blog")
public class BlogController {
  
  final private BlogService blogService;
  
  @GetMapping("/{blogId}")
  public ApiResponse getBlog(@PathVariable("blogId") int blogId) {
    log.info("getting blog");
    Blog blog = this.blogService.findBlog(blogId);
    return ApiResponse.ok(blog);
  }
  
}
