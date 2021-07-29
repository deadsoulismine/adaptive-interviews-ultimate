package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class InterviewForm {
    private long id;
    private int employeeId;
    @NotBlank(message = "Заполните обязательное поле")
    private String employeeName;
    @NotNull(message = "Заполните обязательное поле")
    private Date date;
    @NotBlank(message = "Заполните обязательное поле")
    private String name;
    @NotBlank(message = "Заполните обязательное поле")
    private String nameOfUser;
    private String firstName;
    private String lastName;
    private String description;
    @ToString.Exclude
    private Employee employee;
    @NotEmpty(message = "Выберите пользователей")
    @ToString.Exclude
    private Set<Integer> users = new HashSet<>();
}
