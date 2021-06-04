package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class InterviewForm {

    private Employee employee;
    @NotBlank(message = "Заполните обязательное поле")
    private String employeeName;
    private String description;
    private User user;
    @NotNull(message = "Заполните обязательное поле")
    private Date date;
    @NotBlank(message = "Заполните обязательное поле")
    private String name;
    @NotBlank(message = "Заполните обязательное поле")
    private String nameOfUser;
    private Interview interview;
    private String firstName;
    private String lastName;
    private String date1;

    public String getFirstName() {
        String[] arr = employeeName.split(" ", 2);
        return arr[1];
    }

    public String getLastName() {
        String[] arr = employeeName.split(" ", 2);
        return arr[0];
    }

    public InterviewForm(Interview interview) {
        this.employeeName = interview.getEmployee().getLastName() + " " + interview.getEmployee().getFirstName();
        this.description = interview.getDescription();
        this.date = interview.getDate();
        this.nameOfUser = interview.getUser().getName();
        this.name = interview.getName();
    }

    public InterviewForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
