package com.mooky.blog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiException extends RuntimeException {
  
  private final @Getter String error;
  private final @Getter String errorMessage;
  private final @Getter String errorCode;

  private final String errorValue;
  private final String errorTitle;

  public static class NotFoundException extends ApiException {
    public NotFoundException(String error, String errorMessage, String errorValue, String errorTitle) {
      super(error, errorMessage, "COM_004", errorValue, errorTitle);
    }
  }

  @Override
  public String toString() {
    String toString = "[errorValue=" + errorValue
        + ", error=" + error + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode
        + "]";
    if (this.errorTitle != null) {
      toString = "[" + this.errorTitle + "] " + toString;
    }
    return toString;
  }
}
