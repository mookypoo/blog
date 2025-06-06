package com.mooky.pet_diary.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.pet_diary.domain.user.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Returned after signup or login
 * <p>
 * when login, returns accessToken, which the client should save
 * <p>
 * subsequent requests should always contain header Authorization: Bearer <token>
 */
@JsonInclude(Include.NON_NULL)
@Getter
@RequiredArgsConstructor
public class UserDto {

    private final Long userId;
    private final String username;
    private final String email;
    private String accessToken;

    public UserDto(User userEntity, String accessToken) {
        this.userId = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {     
        return String.format("UserDto(userId=%s, username=%s, email=%s)", 
                this.userId, this.username, this.email);
    }

}
