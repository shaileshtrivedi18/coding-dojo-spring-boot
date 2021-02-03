package com.assignment.spring.exception;

import com.assignment.spring.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    private static final int NOT_FOUND = 101;
    private static final int BAD_REQUEST = 102;
    private static final int UNAUTHORIZED_ERROR = 103;
    private static final int SERVER_ERROR = 104;

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleWebClientNotFoundException(WebClientResponseException exception){
        return error(NOT_FOUND, exception.getStatusCode(), exception.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.Unauthorized.class)
    public ResponseEntity<ErrorResponse> handleWebClientUnAuthorizedException(WebClientResponseException exception){
        return error(UNAUTHORIZED_ERROR, exception.getStatusCode(), exception.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.InternalServerError.class)
    public ResponseEntity<ErrorResponse> handleWebClientInternalServerException(WebClientResponseException exception){
        return error(SERVER_ERROR, exception.getStatusCode(), exception.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleMissingOrIncorrectParameterException(Exception exception){
        return error(BAD_REQUEST, HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception exception){
        return error(SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ErrorResponse> error(int errorCode, HttpStatus httpStatus, String message){
        log.error("Error occurred : code {} description {}", httpStatus.value(), message);
        return ResponseEntity
                .status(httpStatus)
                .body(ErrorResponse.builder().code(errorCode).description(message).build());
    }
}