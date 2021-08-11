package com.smartech.i2019.adaptiveinterviews.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmployeeInterviewsNotFoundException extends RuntimeException {

    public EmployeeInterviewsNotFoundException(String message) {
        super(message);
    }
}
