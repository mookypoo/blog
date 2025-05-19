package com.mooky.blog.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

// currently for testing only
@Component
@Getter
@Setter
@ConfigurationProperties("mooky")
public class SecurityConfig {
  
    private int bCryptStrength;

}
