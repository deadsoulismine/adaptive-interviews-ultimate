package com.smartech.i2019.adaptiveinterviews.util.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SameDataException extends RuntimeException {
    private final String problem;

    public SameDataException(String message, String problem) {
        super(message);
        this.problem = problem;
    }
}
