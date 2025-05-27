package com.mooky.pet_diary.global.config;

import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessTokenSecurity implements RequestMatcher {
    
    @Override
    public boolean matches(HttpServletRequest request) {
        log.debug("access token security matcher");
        String authorization = request.getHeader("authorization");
        if (authorization == null || authorization.length() < 7) { // Bearer <..>
            return false;
        }
        return true;
    }
    
}
