package com.mooky.blog.global.exception;

import lombok.Getter;

/**
 * @param error         describes the error in brief 
 * @param errorMessage  describes the error in detail
 * @param errorCode     error code : begins with COM_###
 * @param errorValue    describes what value caused the error (only for logging purposes)
 */
public class ApiException extends RuntimeException {
  
    private final @Getter String error;
    private final @Getter String errorMessage;
    private final @Getter String errorCode;
    private final String errorValue;

    public ApiException(String error, String errorMessage, String errorCode, String errorValue) {
        super(error);
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorValue = errorValue;
    }

    public static class InUseException extends ApiException {
        /**
         * when a requested email or username, that has passed validation, is already in use
         * follows: error - [resource_type]_in_use, errorMessage - 이미 사용중인 [resource_type]입니다
         * @see ApiException
         */
        public InUseException(String error, String errorMessage, String errorValue) {
            super(error, errorMessage, "COM_001", errorValue);
        }
    }

    public static class NotFoundException extends ApiException {
        /**
         * when a requested resource is not found; 
         * <p> style: [resource_type]_not_found 
         * <p> eg: blog_not_found
         * @see ApiException
         */
        public NotFoundException(String error, String errorMessage, String errorValue) {
            super(error, errorMessage, "COM_004", errorValue);
        }
    }

    @Override
    public String toString() {
        String toString = "[" + this.getClass().getSimpleName() + "] [errorValue=" + errorValue
            + ", error=" + error + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode
            + "]";
        return toString;
    }
}
