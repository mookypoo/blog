package com.mooky.blog.domain.blog.vo;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
//@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogReq {
  
  @Size(max = 50, min = 1)
  private final String title;
  
  @Size(min = 1)
  private final String content;

  private final long userId;

  public BlogReq(String title, String content, long userId) {
    this.title = title;
    this.content = content;
    this.userId = userId;
  }

  public BlogReq() {
    this.title = "";
    this.content = "";
    this.userId = 0;
  }
}
