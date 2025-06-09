package com.mooky.pet_diary.domain.user.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Returned after signup, login, or requesting new access token with refreshToken
 * <p>
 * subsequent requests by client should always contain header Authorization: Bearer <accessToken>
 */
@JsonInclude(Include.NON_NULL)
@Getter
@RequiredArgsConstructor
public class UserDto {

    private final Long userId;
    private final String accessToken;
    private final String refreshToken;

    @Override
    public String toString() {     
        return String.format("UserDto(userId=%s)", this.userId);
    }

}
