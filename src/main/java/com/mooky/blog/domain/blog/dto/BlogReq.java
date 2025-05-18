package com.mooky.blog.domain.blog.dto;

import com.mooky.blog.domain.blog.constraints.SaveBlog;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class BlogReq {
  
  @Size(max = 50, min = 1, message = "title must be between 1 and 50 characters", groups = {SaveBlog.class})
  private final String title;
  
  @Size(min = 1, message = "you must have blog content", groups = { SaveBlog.class })
  private final String content;

  private final long userId; // TODO change to accessToken & cross check

}
