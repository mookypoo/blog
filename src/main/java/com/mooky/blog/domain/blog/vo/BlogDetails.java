package com.mooky.blog.domain.blog.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.blog.domain.blog.entity.BlogEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonInclude(Include.NON_NULL)
// when creating/updating blog, only returns id, title, content 
public class BlogDetails {
  private final long blogId;
  private final String title;
  private final String content;
  private final String authorUsername;
  private final Long authorId;

  public BlogDetails(BlogEntity blogEntity) {
    this.blogId = blogEntity.getId();
    this.title = blogEntity.getTitle();
    this.content = blogEntity.getContent();
    this.authorUsername = blogEntity.getAuthor() == null ? null : blogEntity.getAuthor().getUsername();
    this.authorId = blogEntity.getAuthorId();
  }
}
