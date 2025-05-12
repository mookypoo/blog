package com.mooky.blog.domain.blog.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.blog.domain.blog.entity.BlogEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class BlogDetails {
  private long blogId;
  private String title;
  private String content;
  private String authorUsername;
  private Long authorId;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  public BlogDetails(BlogEntity blogEntity) {
    this.blogId = blogEntity.getId();
    this.title = blogEntity.getTitle();
    this.content = blogEntity.getContent();
    this.authorUsername = blogEntity.getAuthor().getUsername();
    this.authorId = blogEntity.getAuthorId();
    this.createdAt = blogEntity.getCreatedAt();
    this.modifiedAt = blogEntity.getModifiedAt();
  }
}