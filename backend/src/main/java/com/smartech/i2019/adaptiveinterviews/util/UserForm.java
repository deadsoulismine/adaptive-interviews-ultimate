package com.smartech.i2019.adaptiveinterviews.util;

import com.smartech.i2019.adaptiveinterviews.model.UserAutorities;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserForm {
    private String name;
    private String email;
    private String position;
    private String username;
    private String password;
    private UserAutorities userAutorities;
    private String role;
}


