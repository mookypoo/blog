package com.mooky.blog.domain.blog.vo;

import java.time.LocalDateTime;

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
  private final LocalDateTime createdAt;
  private final LocalDateTime modifiedAt;

  public BlogDetails(BlogEntity blogEntity) {
    this.blogId = blogEntity.getId();
    this.title = blogEntity.getTitle();
    this.content = blogEntity.getContent();
    // when creating blog, does not return username
    this.authorUsername = blogEntity.getAuthor() == null ? null : blogEntity.getAuthor().getUsername();
    this.authorId = blogEntity.getAuthorId();
    this.createdAt = blogEntity.getCreatedAt();
    this.modifiedAt = blogEntity.getModifiedAt();
  }
}
