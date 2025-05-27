package com.mooky.pet_diary.global.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mooky.pet_diary.global.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpLogger {
  
    @Autowired
    private HttpServletRequest request;
    private final JwtService jwtService;

    // TODO test url with query string 
    @Around("(@annotation(org.springframework.web.bind.annotation.GetMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PatchMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)) && args(body)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint, final Object body) throws Throwable {
        final String reqMethod = request.getMethod();
        String pathParams = "";
        String bodyString = "";
        if (reqMethod.equals("GET")) {
            final String query = request.getQueryString();
            pathParams = query == null ? "" : "?" + query;
        }
        if (!reqMethod.equals("GET") && body != null) {
         bodyString = "[" + body.getClass() + "]";
        }
        log.info("[HttpRequest] [" + reqMethod + "] " + request.getRequestURI() + pathParams
            + " [userId: " + this.getUserId(request.getHeader("authorization")) + "] [requestId: " + request
                .getHeader("x-request-id") + "] " + bodyString);
        return joinPoint.proceed();
    }

    private String getUserId(String authorizationHeader) {
        if (authorizationHeader != null) {
            String accessToken = authorizationHeader.substring(7);
            String userId = this.jwtService.getUserIdFromAccessToken(accessToken);
            return userId;
        }
        return null;

    }
}
