package com.smartech.i2019.adaptiveinterviews.dto.mapper;

import com.smartech.i2019.adaptiveinterviews.dto.InterviewDTO;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.service.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InterviewMapper {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;

    @Mapping(target = "users", expression =
            "java(userService.findUsersByUsersId(interviewDTO.getUsers()))")
    @Mapping(target = "employee", expression = "java(employeeService.findById(interviewDTO.getEmployeeId()))")
    public abstract Interview interviewDTOtoInterview(InterviewDTO interviewDTO);
}
