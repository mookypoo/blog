package com.mooky.pet_diary.global.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooky.pet_diary.global.ApiResponse;
import com.mooky.pet_diary.global.exception.ApiException;

import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * filters userId from jwt token and sets userId as SpringContextHolder's
 * Authentication
 * 
 * @CurrentUser can be used in any controller method parameter to derive (Long) userId
 * 
 * @return ApiResponse.error with errorCode "AUTH_001" if no jwt token found in authorization header
 * <p> 
 * ApiResponse.error with errorCode "AUTH_002" if invalid jwt token
 * <p>
 * ApiResponse.error with errorCode "AUTH_003" for other exceptions
 */
@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer") || authHeader.length() < 7) {
            this.setErrorResponse(response, ApiResponse.error("auth_error", "AUTH_001", "no jwt token"));
            return;
        }

        try {

            String token = authHeader.substring(7);
            Long userId = this.jwtService.getUserIdFromAccessToken(token);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId,
                        null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.debug("do filter internal exception : type={}", ex.getClass());
            ApiResponse res;
            if (ex instanceof ApiException) {
                res = ApiResponse.error((ApiException) ex);
            } else {
                res = ApiResponse.error("auth_error", "AUTH_003", ex.getMessage());
            }
            this.setErrorResponse(response, res);
        }

    }
    
    private void setErrorResponse(HttpServletResponse response, ApiResponse res) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
        log.warn("[Auth Error] error={}, errorCode={}, errorMessage={}", res.getError(), res.getErrorCode(), res.getErrorMessage());
    }

}
