package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import com.smartech.i2019.adaptiveinterviews.repository.DepartmentRepository;
import com.smartech.i2019.adaptiveinterviews.repository.specification.DepartmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentSpecification departmentSpecification;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void add(Department department) {
        departmentRepository.saveAndFlush(department);
    }

    @Override
    public void delete(long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department findById(long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Department findByName(String name) {
        return departmentRepository.findOne(departmentSpecification.hasName(name)).orElse(null);
    }

    @Override
    public void edit(Department newDepartment) {
        departmentRepository.saveAndFlush(newDepartment);
    }

}
