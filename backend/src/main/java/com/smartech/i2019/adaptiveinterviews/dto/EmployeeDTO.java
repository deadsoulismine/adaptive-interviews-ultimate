package com.smartech.i2019.adaptiveinterviews.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class EmployeeDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String department;
    private Date employmentDate;
    private Date endOfAdaptation;
    private String position;
    private String status;
}
