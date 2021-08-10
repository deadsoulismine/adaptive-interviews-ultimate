package com.smartech.i2019.adaptiveinterviews.dto.mapper;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.dto.EmployeeDTO;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {
    @Autowired
    DepartmentService departmentService;

    @Mapping(target = "department", expression = "java(departmentService.findByName(employeeDTO.getDepartment()))")
    public abstract Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
