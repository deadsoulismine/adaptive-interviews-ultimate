package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAll();

    void add(Department department);

    void delete(long id);

    Department findById(long id);

    Department findByName(String name);

    void edit(Department department);
}
