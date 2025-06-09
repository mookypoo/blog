package com.mooky.pet_diary.global.exception;

/**
 * style: [resource]_not_found
 * <p> 
 * eg: pet_not_found
 */
public class NotFoundException extends ApiException {

    public static NotFoundException resource(String resource, String errorMessage, String errorValue) {
        String error = resource + "_not_found";
        return new NotFoundException(error, errorMessage, "COM_003", errorValue, null, 404);
    }

    private NotFoundException(String error, String errorMessage, String errorCode, String errorValue, String errorTitle,
            int statusCode) {
        super(error, errorMessage, errorCode, errorValue, errorTitle, statusCode);
    }

}
