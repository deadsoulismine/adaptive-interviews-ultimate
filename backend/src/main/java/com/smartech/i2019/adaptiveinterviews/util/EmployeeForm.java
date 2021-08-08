package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class EmployeeForm {
    private long id;
    private String firstName;
    private String lastName;
    private String department;
    private Date employmentDate;
    private Date endOfAdaptation;
    private String position;
    private String status;
    private Employee employee;
}
