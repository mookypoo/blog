package com.mooky.blog.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties("mooky")
public class AppConfig {
  
    private int bCryptStrength;
    
}
