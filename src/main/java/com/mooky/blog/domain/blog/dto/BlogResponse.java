package com.mooky.blog.domain.blog.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.blog.domain.blog.entity.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class BlogResponse {
  private final long blogId;
  private final String title;
  private final String content;
  private final String authorUsername;
  private final Long authorId;
  private final LocalDateTime createdAt;
  private final LocalDateTime modifiedAt;

  public BlogResponse(Blog blogEntity) {
    this.blogId = blogEntity.getId();
    this.title = blogEntity.getTitle();
    this.content = blogEntity.getContent();
    this.authorUsername = blogEntity.getAuthor().getUsername();
    this.authorId = blogEntity.getAuthorId();
    this.createdAt = blogEntity.getCreatedAt();
    this.modifiedAt = blogEntity.getModifiedAt();
  }
}