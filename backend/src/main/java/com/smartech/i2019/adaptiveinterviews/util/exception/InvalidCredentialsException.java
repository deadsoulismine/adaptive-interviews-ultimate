package com.smartech.i2019.adaptiveinterviews.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message, BadCredentialsException ex) {
        super(message, ex);
    }

}
