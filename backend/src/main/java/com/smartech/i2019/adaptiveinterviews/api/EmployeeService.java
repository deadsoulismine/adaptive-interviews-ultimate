package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;

import java.util.List;

public interface EmployeeService {
    void add(Employee employee);

    void edit(Employee employee);

    void delete(long id);

    List<Employee> findAll();

    Employee findById(long id);

    List<Interview> getInterviews(long id);

    List<UploadFile> getUploadFiles(long id);
}
