package com.smartech.i2019.adaptiveinterviews.dto.mapper;

import com.smartech.i2019.adaptiveinterviews.dto.DepartmentDTO;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);
}
