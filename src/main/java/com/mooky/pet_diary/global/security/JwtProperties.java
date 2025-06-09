package com.mooky.pet_diary.global.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
@Validated 
public class JwtProperties {
    
    @NotBlank
    private String secretKey;

    @Positive
    private long accessTokenExpiration;

    @Positive
    private long refreshTokenExpiration;

    @NotBlank
    private String issuer;
}
