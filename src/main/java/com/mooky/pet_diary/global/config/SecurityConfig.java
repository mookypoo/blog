package com.mooky.pet_diary.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mooky.pet_diary.global.security.JwtAuthFilter;
import com.mooky.pet_diary.global.security.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${mooky.endpoint}")
    private String endpoint;
    private final JwtService jwtService;

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(this.jwtService);
    }

    // prevents auto-registration of JwtAuthFilter (don't want it to run on all requests)
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegistration(JwtAuthFilter filter) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false); 
        return registration;
    }

    @Bean
    @Order(1)
    protected SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/v1/auth/**")
            .anonymous(anonymous -> anonymous.disable()) // no "annonymous" authenticated user
            .csrf((csrf) -> csrf.disable());
        return http.build();
    }

    @Bean
    @Order(2)
    protected SecurityFilterChain privateSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/v1/**") // all other endpoints
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
