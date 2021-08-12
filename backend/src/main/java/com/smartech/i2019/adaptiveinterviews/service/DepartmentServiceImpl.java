package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import com.smartech.i2019.adaptiveinterviews.repository.DepartmentRepository;
import com.smartech.i2019.adaptiveinterviews.repository.specification.DepartmentSpecification;
import com.smartech.i2019.adaptiveinterviews.util.exception.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentSpecification departmentSpecification;
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

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
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            logger.warn("Отдела с указанным идентификатором ({}) нет в базе данных", id);
            throw new DepartmentNotFoundException("Отдела с указанным идентификатором нет в базе данных");
        }
        return department;
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
