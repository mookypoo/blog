package com.mooky.pet_diary.global.exception;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mooky.pet_diary.global.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        log.warn("{} [{}]", ex.toString(), ex.getStackTrace()[0].toString());
        return ResponseEntity.status(ex.getStatusCode()).body(ApiResponse.error(ex));
    }

    /**
     * <pre>
     * when using @Valid or @Validated (Jakarta Constraints)
     * 
     * @see InvalidArgumentException
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
        log.warn("[MethodArgumentNotValidException] {}; fieldName={}; rejectedValue={}",
                message, fieldName, rejectedValue);
        return ResponseEntity.status(400).body(ApiResponse.error("invalid_argument", "COM_002", message));
    }

    /**
     * @see InvalidArgumentException
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ApiResponse> handleJsonMappingException(JsonMappingException ex) {
        String errorMessage = "there was a problem processing your data; please check them again";
        String logError = ex.getMessage();
        if (ex.getCause() instanceof DateTimeParseException) {
            logError = "DateTimeParseException " + ((DateTimeParseException) ex.getCause()).getParsedString();
            errorMessage = "date should be YYYY-MM-DD format";
        }
        log.warn("[JsonMappingException] {}", logError);
        return ResponseEntity.status(400).body(ApiResponse.error("invalid_argument", "COM_002", errorMessage));
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
