package com.mooky.blog.domain.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mooky.blog.domain.blog.constraints.SaveBlog;
import com.mooky.blog.domain.blog.dto.BlogDetailsDto;
import com.mooky.blog.domain.blog.dto.BlogWriteDto;
import com.mooky.blog.global.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


@RestController
@RequestMapping("${mooky.endpoint}/v1/blogs")
public class BlogController {
  
    @Autowired
    private BlogService blogService;

    // TODO get all blogs
    @GetMapping
    public ApiResponse getAllBlogs() {
        return ApiResponse.ok("all blogs");
    }
    
    
    @GetMapping("/{blogId}")
    public ApiResponse getBlogDetails(@PathVariable("blogId") int blogId) {
        BlogDetailsDto blog = this.blogService.findBlogAndReturnBlogDetails(Integer.toUnsignedLong(blogId));
        return ApiResponse.ok(blog);
    }


    @GetMapping("/nativeQuery/{blogId}")
    public ApiResponse getBlogDetailsWithNativeQuery(@PathVariable("blogId") int blogId) {
        BlogDetailsDto blog = this.blogService.findBlogUsingNativeQuery(Integer.toUnsignedLong(blogId));
        return ApiResponse.ok(blog);
    }

    // TODO validator
    @PostMapping
    public ApiResponse createBlog(@Valid @RequestBody BlogWriteDto blogReq) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(blogReq, SaveBlog.class);
        BlogDetailsDto blog = this.blogService.saveBlogAndReturnBlogDetails(blogReq);
        return ApiResponse.ok(blog);
    }

    @PatchMapping("/{blogId}")
    public ApiResponse editBlog(@Valid @RequestBody BlogWriteDto blogReq, @PathVariable("blogId") int blogId) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(blogReq, SaveBlog.class);
        BlogDetailsDto blog = this.blogService.editBlogAndReturnBlogDetails(Integer.toUnsignedLong(blogId), blogReq);
        return ApiResponse.ok(blog);
    }
  
}
