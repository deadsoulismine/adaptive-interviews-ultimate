package com.smartech.i2019.adaptiveinterviews.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "interviews")
public class Interview {
    private int id;
    private Employee employee;
    private String description;
    private User user;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return (User) Hibernate.unproxy(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    public Employee getEmployee() {
        return (Employee) Hibernate.unproxy(employee);
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
