package com.mooky.blog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @param error         describes the error in brief the error (eg. blog_not_found)
 * @param errorMessage  describes the error in detail
 * @param errorCode     error code : begins with COM_###
 * @param errorValue    describes what value caused the error
 * @param errorTitle    for logging purposes - begins the log with [errorTitle]
 */
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
  
    private final @Getter String error;
    private final @Getter String errorMessage;
    private final @Getter String errorCode;

    private final String errorValue;
    private final String errorTitle;

    public static class NotFoundException extends ApiException {
        /**
         * when a requested resource is not found; 
         * @see ApiException
         */
        public NotFoundException(String error, String errorMessage, String errorValue) {
            super(error, errorMessage, "COM_004", errorValue, null);
        }
    }

    // public static class InvalidBodyException extends ApiException {
    //   /**
    //    * when body values do not meet format requirements
    //    * <p> eg) invalid username format
    //    * @see ApiException
    //    */
    //   public InvalidBodyException(String error, String errorMessage, String errorValue) {
    //     super(error, errorMessage, "COM_001", errorValue, null);
    //   }
    // }


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
