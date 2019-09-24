package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserForm {
    @NotBlank(message = "Заполните обязательное поле")
    private String name;
    @NotBlank(message = "Заполните обязательное поле")
    @Email(message = "Некорректный email адрес")
    private String email;
    @NotBlank(message = "Заполните обязательное поле")
    private String username;
    @NotBlank(message = "Заполните обязательное поле")
    private String password;
    private UserAutorities userAutorities;
    private String role;

    public UserForm() {
    }

    public UserForm(UserAutorities userAutorities) {
        this.name = userAutorities.getUser().getName();
        this.email = userAutorities.getUser().getEmail();
        this.username = userAutorities.getUsername();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
