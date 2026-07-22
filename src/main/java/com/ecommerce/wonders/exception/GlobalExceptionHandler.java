package com.ecommerce.wonders.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> errorDetails = getErrorDetails(ex.getMessage(), "Bad Request", status.value());

        return new ResponseEntity<>(errorDetails, status);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseViolation(DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        Map<String, Object> errorDetails =  getErrorDetails(ex.getMessage(), "Data Integrity Violation", status.value());
        
        return new ResponseEntity<>(errorDetails, status);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> errorDetails = getErrorDetails(ex.getMessage(), status.getReasonPhrase(), status.value());

        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> errorDetails = getErrorDetails(ex.getMessage(), status.getReasonPhrase(), status.value());

        return new ResponseEntity<>(errorDetails, status);
    }

    private Map<String, Object> getErrorDetails(String message, String error, int status) {
        Map<String, Object> errorDetails = new HashMap<>();

        errorDetails.put("message", message);
        errorDetails.put("error", error);
        errorDetails.put("status", String.valueOf(status));

        return errorDetails;
    }
}
