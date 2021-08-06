package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class InterviewForm {
    private long id;
    private int employeeId;
    private String employeeName;
    private Date date;
    private String name;
    private String nameOfUser;
    private String firstName;
    private String lastName;
    private String description;
    @ToString.Exclude
    private Employee employee;
    @ToString.Exclude
    private Set<Integer> users = new HashSet<>();
}
