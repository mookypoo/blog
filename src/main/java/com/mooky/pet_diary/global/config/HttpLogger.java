package com.mooky.pet_diary.global.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class HttpLogger {
  
    @Autowired
    private HttpServletRequest request;

    // TODO test url with query string 
    @Around("(@annotation(org.springframework.web.bind.annotation.GetMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.PatchMapping)"
        + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        final String reqMethod = request.getMethod();
        String pathParams = "";
        String bodyString = "";
        if (reqMethod.equals("GET")) {
            final String query = request.getQueryString();
            pathParams = query == null ? "" : "?" + query;
        }

        if (!reqMethod.equals("GET")) {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg != null && !(arg instanceof Long)) { // Skip @CurrentUser Long
                    bodyString = " " + arg.toString();
                    break;
                }
            }
        }

        Long userId = SecurityContextHolder.getContext().getAuthentication() == null ? null
                : (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        log.info("[HttpRequest] [{}] {}{} [userId={}, requestId={}] {}", reqMethod, request.getRequestURI(), pathParams,
                userId, request.getHeader("x-request-id"), bodyString);
        return joinPoint.proceed();
    }

}
