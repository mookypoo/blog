package com.mooky.pet_diary.global.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

/**
 * <pre>
 * COM_001 : InUseException
 * COM_002 : InvalidArgumentException 
 * = error code applies for MethodArgumentNotValidException, JsonMappingException
 * COM_003 : NotFoundException
 * </pre>
 * 
 * @param error        describes the error in brief
 * @param errorMessage describes the error in detail
 * @param errorCode    error code : ***_### format
 * @param errorValue   describes what value caused the error (only for logging
 *                     purposes)
 * @param errorTitle   title for error (only for logging purposes) (eg. 로그인 실패)
 */
public abstract class ApiException extends RuntimeException {
  
    private final @Getter String error;
    private final @Getter String errorMessage;
    private final @Getter String errorCode;
    private final String errorValue;
    private final String errorTitle;
    private final @Getter @JsonIgnore int statusCode;

    public ApiException(String error, String errorMessage, String errorCode, String errorValue, String errorTitle,
            int statusCode) {
        super(error);
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorValue = errorValue;
        this.errorTitle = errorTitle;
        this.statusCode = statusCode;
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
