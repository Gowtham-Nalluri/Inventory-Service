package com.hcl.inventory.exception;

import com.hcl.inventory.constants.InventoryConstants;
import com.hcl.inventory.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(ValidationException ex) {

        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .errorCode(
                                InventoryConstants.VALIDATION_ERROR_CODE)
                        .message(
                                "Validation Failed")
                        .details(
                                ex.getErrors())
                        .timestamp(
                                LocalDateTime.now())
                        .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {

        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .errorCode(
                                InventoryConstants.INTERNAL_ERROR_CODE)
                        .message(
                                ex.getMessage())
                        .details(
                                Collections.emptyList())
                        .timestamp(
                                LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}