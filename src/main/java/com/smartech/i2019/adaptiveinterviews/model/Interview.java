package com.smartech.i2019.adaptiveinterviews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "employee", nullable = false)
    private Employee employee;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "date", nullable = false)
    private Date date;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    public User getUser() {
//        return (User) Hibernate.unproxy(user);
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }

//
//    @ManyToOne
//    @JoinColumn(name = "employee", nullable = false)
//    public Employee getEmployee() {
//        return (Employee) Hibernate.unproxy(employee);
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
}
