package com.smartech.i2019.adaptiveinterviews.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserDisabledException extends RuntimeException {

    public UserDisabledException(String message, DisabledException ex) {
        super(message, ex);
    }
}
