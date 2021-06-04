package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.Employee;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class EmployeeForm {
    public EmployeeForm() {
    }

    private int id;
    @NotBlank(message = "Заполните обязательное поле")
    private String firstName;
    @NotBlank(message = "Заполните обязательное поле")
    private String lastName;
    private String department;
    private Date employmentDate;
    private String date1;
    private String date2;
    private Date endOfAdaptation;
    @NotBlank(message = "Заполните обязательное поле")
    private String position;
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Date getEndOfAdaptation() {
        return endOfAdaptation;
    }

    public void setEndOfAdaptation(Date endOfAdaptation) {
        this.endOfAdaptation = endOfAdaptation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private Employee employee;

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public EmployeeForm(Employee employee) {
        this.employee = employee;
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.department = employee.getDepartment().getName();
        this.employmentDate = employee.getEmploymentDate();
        this.endOfAdaptation = employee.getEndOfAdaptation();
        this.position = employee.getPosition();
        this.status = employee.getStatus();
        this.id = employee.getId();
    }
}
