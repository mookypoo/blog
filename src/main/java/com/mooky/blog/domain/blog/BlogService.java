package com.mooky.blog.domain.blog;

import org.springframework.stereotype.Service;

import com.mooky.blog.domain.blog.entity.BlogEntity;
import com.mooky.blog.domain.blog.repository.BlogRepository;
import com.mooky.blog.domain.blog.vo.BlogDetails;
import com.mooky.blog.domain.blog.vo.BlogReq;
import com.mooky.blog.global.exception.ApiException.NotFoundException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogService {

  private final BlogRepository BlogRepository;

  public long saveBlogAndReturnId(BlogReq blogReq) {
    BlogEntity savedBlog = this.BlogRepository.save(new BlogEntity(blogReq));
    return savedBlog.getId();
  }

  public BlogDetails findBlog(int blogId) {
    BlogEntity blogEntity = this.BlogRepository.findById(blogId).orElseThrow(() -> 
      new NotFoundException("blog_not_found", "없는 블로그입니다.", String.valueOf(blogId), null));
    
    return new BlogDetails(blogEntity);
  }

}
