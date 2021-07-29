package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import com.smartech.i2019.adaptiveinterviews.repository.EmployeeRepository;
import com.smartech.i2019.adaptiveinterviews.util.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSpecification employeeSpecification;

    @Override
    public void add(Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void edit(Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee findByName(String firstName, String lastName) {
        return (Employee) employeeRepository.findAll(Specification.where(
                employeeSpecification.hasFirstName(firstName).and(
                        employeeSpecification.hasLastName(lastName))));
    }

    @Override
    public Employee findByLastName(String lastName) {
        return employeeRepository.findOne(employeeSpecification.hasLastName(lastName)).orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> listByLastName(String lastName) {
        return employeeRepository.findAll(employeeSpecification.hasLastName(lastName));
    }

    @Override
    public List<Employee> listByStatus(String status) {
        return employeeRepository.findAll(employeeSpecification.hasStatus(status));
    }

    @Override
    public List<Interview> getInterviews(long id) {
        return Objects.requireNonNull(employeeRepository.findById(id).orElse(null)).getInterviews();
    }

    @Override
    public List<UploadFile> getUploadFiles(long id) {
        return Objects.requireNonNull(employeeRepository.findById(id).orElse(null)).getFiles();
    }

}
