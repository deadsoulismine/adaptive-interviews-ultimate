package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import com.smartech.i2019.adaptiveinterviews.repository.EmployeeRepository;
import com.smartech.i2019.adaptiveinterviews.util.exception.EmployeeFilesNotFoundException;
import com.smartech.i2019.adaptiveinterviews.util.exception.EmployeeInterviewsNotFoundException;
import com.smartech.i2019.adaptiveinterviews.util.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

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
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            logger.warn("Попытка получить сотрудника, которого нет в базе данных, с идентификатором: ({})", id);
            throw new EmployeeNotFoundException("Сотрудника с указанным идентификатором нет в базе данных");
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Interview> getInterviews(long id) {
        List<Interview> interviews = findById(id).getInterviews();
        if (interviews.isEmpty()) {
            logger.warn("С данным сотрудником не проводили собеседований: ({})", findById(id));
            throw new EmployeeInterviewsNotFoundException("С данным сотрудником не проводили собеседований");
        }
        return interviews;
    }

    @Override
    public List<UploadFile> getUploadFiles(long id) {
        List<UploadFile> files = findById(id).getFiles();
        if (files.isEmpty()) {
            logger.warn("Нет загруженных файлов для данного сотрудника: ({})", findById(id));
            throw new EmployeeFilesNotFoundException("Нет загруженных файлов для данного сотрудника");
        }
        return files;
    }

}
