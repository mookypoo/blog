package com.mooky.blog.global.exception;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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

  // TODO ex.getBindingResult().getFieldError() vs. ex.getFieldError()
  /**
   * Jakarta constraint 사용했을 시 error (annoated within class)
   * 
   * @param ex
   * @return
   */
  @SuppressWarnings("null")
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
   
    FieldError fieldError = ex.getFieldError();
    String fieldName = "";
    String rejectedValue = "";
    if (fieldError != null) {
      fieldName = fieldError.getField();
      rejectedValue = fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "";
    }
    String message = fieldError != null ? fieldError.getDefaultMessage() : "invalid body argument";
    log.warn(
        "[MethodArgumentNotValidException] {}; fieldName={}; rejectedValue={}",
        message, fieldName, rejectedValue);
    return ResponseEntity.status(400).body(ApiResponse.error("invalid_argument", "COM_001", message));
  }


  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ApiResponse> handleSqlException(SQLException ex) {
    int status = 500;
    String error = "sql_exception";
    String errorMessage = "please contact the server";
    String errorCode = "DB_001";
    if (ex instanceof SQLIntegrityConstraintViolationException) {
      status = 400;
      error = "invalid_data";
      errorMessage = "there was a problem processing your data; please check them again";
      errorCode = "DB_002";
    }
    log.warn("[SqlException] {}", ex.getMessage());
    return ResponseEntity.status(status).body(ApiResponse.error(error, errorCode, errorMessage));
  }

}
