package com.smartech.i2019.adaptiveinterviews.dto.mapper;

import com.smartech.i2019.adaptiveinterviews.api.UsersAuthoritiesService;
import com.smartech.i2019.adaptiveinterviews.dto.UserDTO;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.model.UserAuthorities;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UsersAuthoritiesService usersAuthoritiesService;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDTO.getPassword()))")
    public abstract UserAuthorities userDTOToUserAuthorities(UserDTO userDTO);

    public abstract User userDTOToUser(UserDTO userDTO);
}
