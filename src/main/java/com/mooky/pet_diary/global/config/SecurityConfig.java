package com.mooky.pet_diary.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${mooky.endpoint}")
    private String endpoint;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String v1Endpoint = this.endpoint.isEmpty() ? "/v1" : "/" + this.endpoint + "/v1";
        http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(v1Endpoint + "/auth/**")
                        .permitAll()
                        .requestMatchers(new AccessTokenSecurity()).permitAll())
                .csrf((csrf) -> csrf.disable());
        return http.build();
    }
}
