package com.clone.kakaoclone.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class})
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setResponse(false);
        restApiException.setMessage(ex.getMessage());

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<RestApiException> handleUserRequestException (MethodArgumentNotValidException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setResponse(false);
        restApiException.setMessage(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

}