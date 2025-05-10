package com.mooky.blog.domain.blog.vo;

import com.mooky.blog.domain.blog.entity.BlogEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Blog {
  private final long id;
  private final String title;
  private final String content;
  private final String username;
  private final long userId;

  public Blog(BlogEntity blogEntity) {
    this.id = blogEntity.getId();
    this.title = blogEntity.getTitle();
    this.content = blogEntity.getContent();
    this.username = blogEntity.getUser().getUsername();
    this.userId = blogEntity.getUser().getId();
  }
}
