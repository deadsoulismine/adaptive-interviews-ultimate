package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    void add(Employee employee);

    void edit(Employee employee);

    void delete(long id);

    Employee findById(long id);

    Employee findByName(String firstName, String lastName);

    List<Employee> findByLastName(String lastName);

    List<Employee> findAll();

    List<Employee> listForNewInterview(String name);

    List<Employee> listByLastName(String lastName);

    List<Employee> listInAdaptation(String name);

    Set<Interview> getInterviews(long id);
}
