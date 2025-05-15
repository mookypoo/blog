package com.mooky.blog.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetails {

  private final Long userId;

  private final String username;

  private final String email;

  //private String profileImage;

}
