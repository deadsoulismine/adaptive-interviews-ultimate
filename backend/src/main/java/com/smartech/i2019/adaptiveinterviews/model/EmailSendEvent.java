package com.smartech.i2019.adaptiveinterviews.model;

import lombok.Data;

import java.util.Date;

@Data
public class EmailSendEvent {
    private String email;
    private String username;
    private String employeeFullName;
    private Date date;
}
