package com.mooky.blog.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mooky.blog.global.exception.ApiException;

import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class ApiResponse {
  
  private final String result; // success or error
  private Object payload;
  private String error;
  private String errorMessage;
  private String errorCode;

  // success 
  private ApiResponse(Object payload) {
    this.result = "success";
    this.payload = payload;
  }

  static public ApiResponse ok(Object payload) {
    return new ApiResponse(payload);
  }

  // error
  private ApiResponse(String error, String errorCode, String errorMessage) {
    this.result = "error";
    this.error = error;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  static public ApiResponse error(ApiException ex) {
    return new ApiResponse(ex.getError(), ex.getErrorCode(), ex.getErrorMessage());
  }

  static public ApiResponse error(String error, String errorCode, String errorMessage) {
    return new ApiResponse(error, errorCode, errorMessage);
  }
 
  public String toString() {
    return this.payload.toString();
  }
}
