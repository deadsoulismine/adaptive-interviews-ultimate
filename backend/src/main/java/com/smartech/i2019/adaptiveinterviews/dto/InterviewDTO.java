package com.smartech.i2019.adaptiveinterviews.dto;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class InterviewDTO {
    private long id;
    private String name;
    private String description;
    private Date date;
    private int employeeId;
    @ToString.Exclude
    private Set<Long> users = new HashSet<>();
}
