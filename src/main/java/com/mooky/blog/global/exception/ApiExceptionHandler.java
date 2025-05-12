package com.mooky.blog.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mooky.blog.global.ApiResponse;
import com.mooky.blog.global.exception.ApiException.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

  private String getThrownFrom(StackTraceElement[] stackTrace) {
    return stackTrace[0].toString();
  }
  
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
    int status = 400;
    log.warn("[{}] {} [{}]", ex.getClass().getSimpleName(), ex.toString(), ex.getStackTrace()[0].toString());

    if (ex instanceof NotFoundException) {
      status = 404;
    }
      
    return ResponseEntity.status(status).body(ApiResponse.error(ex));
  }

  // Jakarta constraint 사용했을 시 error (annoated within class)
  @SuppressWarnings("null")
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    
    String message = ex.getFieldError() == null ? null : ex.getFieldError().getDefaultMessage();
    if (message == null) {
      message = "파라미터 " + ex.getFieldError().getField() + " 오류 : " + ex.getFieldError().getRejectedValue();
    }
    log.warn("[MethodArgumentNotValidException] {} [{}]", ex.getMessage(), this.getThrownFrom(ex.getStackTrace()));
    return ResponseEntity.status(400).body(ApiResponse.error("invalid_argument", "COM_001", message));
  }

}
