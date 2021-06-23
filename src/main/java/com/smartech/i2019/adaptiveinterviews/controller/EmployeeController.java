package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.DepartmentDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.util.EmployeeForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping("/employees")
@RestController
@Tag(name="Сотрудники", description="Взаимодействие с сотрудниками")
public class EmployeeController {
    @Autowired
    private DepartmentDaoImpl departmentDao;
    @Autowired
    private EmployeeDaoImpl employeeDao;

    @Operation(summary = "Показать всех сотрудников")
    @GetMapping()
    ResponseEntity<List<Employee>> allEmployees() {
        List<Employee> employees = employeeDao.list();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Найти сотрудника по ID")
    @GetMapping("/{id}")
    ResponseEntity<Employee> findEmployee(@PathVariable @Min(1) int id) throws EntityNotFoundException {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Добавить сотрудника")
    @PostMapping("/add")
    ResponseEntity<Employee> addEmployee(@RequestBody EmployeeForm form) {
        Employee employee = new Employee();
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());
        employee.setDepartment(departmentDao.getByName(form.getDepartment()));
        employee.setEmploymentDate(form.getEmploymentDate());
        employee.setEndOfAdaptation(form.getEndOfAdaptation());
        employee.setStatus(form.getStatus());
        employee.setPosition(form.getPosition());
        employeeDao.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные сотрудника")
    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable int id) {
        employeeDao.update(newEmployee, id);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @Operation(summary = "Удалить сотрудника")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        employeeDao.delete(id);
        return new ResponseEntity<>("Сотрудник удален", HttpStatus.OK);
    }

}


