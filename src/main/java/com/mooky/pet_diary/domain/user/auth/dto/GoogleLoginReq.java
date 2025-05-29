package com.mooky.pet_diary.domain.user.auth.dto;

import com.mooky.pet_diary.domain.user.constraints.EmailConstraints;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleLoginReq {
    
    @EmailConstraints
    private final String email;

    @NotNull(message = "google id token을 입력해주세요")
    @NotEmpty(message = "google id token을 입력해주세요")
    private final String googleIdToken;

}
