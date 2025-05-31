package com.mooky.pet_diary.global.exception;

import lombok.Getter;

/**
 * @param error         describes the error in brief 
 * @param errorMessage  describes the error in detail
 * @param errorCode     error code : begins with COM_###
 * @param errorValue    describes what value caused the error (only for logging purposes)
 * @param errorTitle    title for error (only for logging purposes) (eg. 로그인 실패)
 */
public class ApiException extends RuntimeException {
  
    private final @Getter String error;
    private final @Getter String errorMessage;
    private final @Getter String errorCode;
    private final String errorValue;
    private final String errorTitle;

    public ApiException(String error, String errorMessage, String errorCode, String errorValue, String errorTitle) {
        super(error);
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorValue = errorValue;
        this.errorTitle = errorTitle;
    }

    public static class InUseException extends ApiException {
        /**
         * when a requested email or username, that has passed validation, is already in use
         * <p> follows: error - [resource_type]_in_use, errorMessage - 이미 사용중인 [resource_type]입니다
         * @see ApiException
         */
        public InUseException(String error, String errorMessage, String errorValue, String errorTitle) {
            super(error, errorMessage, "COM_001", errorValue, errorTitle);
        }
    }

    public static class InvalidArgsException extends ApiException {
        /**
         * @see ApiException
         */
        public InvalidArgsException(String error, String errorMessage, String errorValue, String errorTitle) {
            super(error, errorMessage, "COM_002", errorValue, errorTitle);
        }
    }

    public static class AuthException extends ApiException {
        /**
         * @see ApiException
         */
        public AuthException(String error, String errorMessage, String errorValue, String errorTitle) {
            super(error, errorMessage, "AUTH_003", errorValue, errorTitle);
        }
    }

    public static class NotFoundException extends ApiException {
        /**
         * when a requested resource is not found; 
         * <p> style: [resource_type]_not_found 
         * <p> eg: blog_not_found
         * @see ApiException
         */
        public NotFoundException(String error, String errorMessage, String errorValue, String errorTitle) {
            super(error, errorMessage, "COM_004", errorValue, errorTitle);
        }
    }

    @Override
    public String toString() {
        String toString = "[" + this.getClass().getSimpleName() + "] [errorValue=" + this.errorValue
            + ", error=" + this.error + ", errorMessage=" + this.errorMessage + ", errorCode=" + this.errorCode
                + "]";
        if (this.errorTitle != null) {
            toString = "[" + this.errorTitle + "]";
        }
        return toString;
    }
}
