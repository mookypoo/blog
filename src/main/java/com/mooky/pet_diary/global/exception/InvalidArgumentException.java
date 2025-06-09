package com.mooky.pet_diary.global.exception;

/**
 * same error code as when handling MethodArgumentNotValidException,
 * JsonMappingException
 * 
 * @see ApiExceptionHandler
 */

public class InvalidArgumentException extends ApiException {

    public static InvalidArgumentException type(String argumentType, String errorMessage, String errorValue) {
        String error = "invalid_argument_" + argumentType;
        return new InvalidArgumentException(error, errorMessage, errorValue);
    }

    private InvalidArgumentException(String error, String errorMessage, String errorValue) {
        super(error, errorMessage, "COM_002", errorValue, null, 400);
    }

}

