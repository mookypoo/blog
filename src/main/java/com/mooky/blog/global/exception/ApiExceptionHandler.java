package com.mooky.blog.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mooky.blog.global.ApiResponse;

@ControllerAdvice
public class ApiExceptionHandler {
  
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
    int status = 400;
    return ResponseEntity.status(status).body(ApiResponse.error(ex));
  }

}
