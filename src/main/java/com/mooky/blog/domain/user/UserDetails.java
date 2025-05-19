package com.mooky.blog.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDetails {

    private final Long userId;

    private final String username;

    private final String email;

    //private String profileImage;

}
