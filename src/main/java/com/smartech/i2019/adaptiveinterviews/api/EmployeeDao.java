package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;

import java.util.List;
import java.util.Set;

public interface EmployeeDao {
    void add(Employee employee);

    void update(Employee employee, int id);

    void delete(int id);

    Employee getById(int id);

    List<Employee> list();

    List<Employee> getByLastName(String lastName);

    List<Employee> listForNewInterview();

    List<Employee> listByLastName(String lastName);

    List<Employee> listInAdaptation();

    Set<Interview> getInterviews(int id);

    Employee getByName(String firstName, String lastName);
}
