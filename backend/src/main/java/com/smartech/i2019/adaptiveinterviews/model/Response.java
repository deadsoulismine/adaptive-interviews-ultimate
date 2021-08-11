package com.smartech.i2019.adaptiveinterviews.model;

import lombok.Data;

@Data
public class Response {
    private String message;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

}
