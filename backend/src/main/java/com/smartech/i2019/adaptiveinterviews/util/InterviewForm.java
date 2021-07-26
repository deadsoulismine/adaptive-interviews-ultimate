package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class InterviewForm {
    private long id;
    private Employee employee;
    @NotBlank(message = "Заполните обязательное поле")
    private String employeeName;
    private String description;
    @NotNull(message = "Заполните обязательное поле")
    private Date date;
    @NotBlank(message = "Заполните обязательное поле")
    private String name;
    @NotBlank(message = "Заполните обязательное поле")
    private String nameOfUser;
    @NotEmpty(message = "Выберите пользователей")
    private Set<Integer> users = new HashSet<>();
    private Interview interview;
    private String firstName;
    private String lastName;
}
