package com.google.legal_sales_sidekick.exception.handler;

import com.google.legal_sales_sidekick.exception.AuthenticationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(final AuthenticationException authenticationException) {
        return new ResponseEntity<>(authenticationException.getMessage(), HttpStatusCode.valueOf(401));
    }
}
