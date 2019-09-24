package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Department;

import java.util.List;

public interface DepartmentDao {
    void add(Department department);

    void update(Department department, int id);

    void delete(int id);

    Department getByName(String name);

    Department getById(int id);

    List<Department> list();
}
