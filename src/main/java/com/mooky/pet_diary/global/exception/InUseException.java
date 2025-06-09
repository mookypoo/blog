package com.mooky.pet_diary.global.exception;

public class InUseException extends ApiException {

    private InUseException(String error, String errorMessage, String errorValue) {
        super(error, errorMessage, "COM_001", errorValue, null, 400);
    }

    public static InUseException resource(String resource, String errorValue) {
        String error = resource + "_in_use";
        String errorMessage = resource + " is already being used";
        return new InUseException(error, errorMessage, errorValue);
    }
    
}
