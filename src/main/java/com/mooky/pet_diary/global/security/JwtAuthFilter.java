package com.mooky.pet_diary.global.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooky.pet_diary.global.ApiResponse;
import com.mooky.pet_diary.global.exception.ApiException;

import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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
 * ApiResponse.error with errorCode "AUTH_003" for other auth exceptions
 */
// @Slf4j
// //@Component
// @RequiredArgsConstructor
// public class JwtAuthFilter extends OncePerRequestFilter {

//     private final JwtService jwtService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         log.debug("do filter internal");
//         String token = this.extractTokenFromRequest(request);
//         if (token == null) {
//             this.setErrorResponse(response, ApiResponse.error("auth_error", "AUTH_001", "no jwt token"));
//             return;
//         }

//         try {
//             this.authenticateUser(token);
//             filterChain.doFilter(request, response);

//         } catch (ServletException ex) {
//             log.error("[Servlet Exception] {}", ex.getMessage());
//             throw ex;
//         } catch (IOException ex) {
//             log.error("[IO Exception] {}", ex.getMessage());
//             throw ex;
//         } catch (ApiException ex) {
//             this.setErrorResponse(response, ApiResponse.error(ex));
//         } catch (Exception ex) {
//             this.setErrorResponse(response, ApiResponse.error("auth_error", "AUTH_003", ex.getMessage()));
//         }
//     }

//     /**
//      * @return null if not valid authorization header
//      */
//     private String extractTokenFromRequest(HttpServletRequest request) {
//         String authHeader = request.getHeader("Authorization");
//         if (authHeader == null || !authHeader.startsWith("Bearer") || authHeader.length() < 7) {
//             return null;
//         }
//         return authHeader.substring(7);
//     }

//     /**
//      * sets the userId as SecurityContextHolder's authentication, which is later used to find out @CurrentUser
//      * @see CurrentUser
//      */
//     private void authenticateUser(String token) {
//         Long userId = this.jwtService.getUserIdFromAccessToken(token);
//         if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId,
//                     null, Collections.emptyList());
//             SecurityContextHolder.getContext().setAuthentication(authToken);
//         }
//     }

//     private void setErrorResponse(HttpServletResponse response, ApiResponse res) throws JsonProcessingException, IOException {
//         response.setStatus(HttpStatus.UNAUTHORIZED.value());
//         response.setContentType("application/json");
//         response.setCharacterEncoding("UTF-8");
//         response.getWriter().write(new ObjectMapper().writeValueAsString(res));
//         log.warn("[Auth Error] error={}, errorCode={}, errorMessage={}", res.getError(), res.getErrorCode(), res.getErrorMessage());
//     }

// }

@Slf4j
// @Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtService jwtService;

    /**
     * @return null if not valid authorization header
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer") || authHeader.length() < 7) {
            return null;
        }
        return authHeader.substring(7);
    }

    /**
     * sets the userId as SecurityContextHolder's authentication, which is later
     * used to find out @CurrentUser
     * 
     * @see CurrentUser
     */
    private void authenticateUser(String token) {
        Long userId = this.jwtService.getUserIdFromAccessToken(token);
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId,
                    null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ApiResponse res)
            throws JsonProcessingException, IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
        log.warn("[Auth Error] error={}, errorCode={}, errorMessage={}", res.getError(), res.getErrorCode(),
                res.getErrorMessage());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = this.extractTokenFromRequest(httpRequest);
        if (token == null) {
            this.setErrorResponse(httpResponse, ApiResponse.error("auth_error", "AUTH_001", "no jwt token"));
            return;
        }

        try {
            this.authenticateUser(token);
            chain.doFilter(httpRequest, httpResponse);

        } catch (ServletException ex) {
            log.error("[Servlet Exception] {}", ex.getMessage());
            throw ex;
        } catch (IOException ex) {
            log.error("[IO Exception] {}", ex.getMessage());
            throw ex;
        } catch (ApiException ex) {
            this.setErrorResponse(httpResponse, ApiResponse.error(ex));
        } catch (Exception ex) {
            this.setErrorResponse(httpResponse, ApiResponse.error("auth_error", "AUTH_003", ex.getMessage()));
        }
    }

}
