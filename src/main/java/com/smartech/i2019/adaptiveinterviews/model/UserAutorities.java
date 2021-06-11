package com.smartech.i2019.adaptiveinterviews.model;

import org.hibernate.Hibernate;

import javax.persistence.*;

@Entity
@Table(name = "users_autorities")
public class UserAutorities {
    private String username;
    private String password;
    private String role;
    private User user;
    private int user_id;

    @Id
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @MapsId
    public User getUser() {
        return (User) Hibernate.unproxy(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "username", nullable = false)

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
