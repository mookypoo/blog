package com.mooky.blog.domain.user.auth.dto;

import com.mooky.blog.domain.user.constraints.EmailConstraints;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginReq {
    @EmailConstraints
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @NotNull(message = "비밀번호를 입력해주세요")
    private final String password;

}
