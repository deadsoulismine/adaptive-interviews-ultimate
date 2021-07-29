package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.util.EmployeeForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RequestMapping("/api/employees")
@RestController
@Tag(name = "Сотрудники", description = "Взаимодействие с сотрудниками")
@RequiredArgsConstructor
public class EmployeeController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    @Operation(summary = "Показать всех сотрудников")
    @GetMapping()
    ResponseEntity<List<Employee>> allEmployees() {
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Получить список бесед по ID пользователя")
    @GetMapping("/interviews/{id}")
    ResponseEntity<List<Interview>> getInterviews(@PathVariable @Min(1) long id) throws EntityNotFoundException {
        List<Interview> interviews = employeeService.getInterviews(id);
        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }

    @Operation(summary = "Найти сотрудника по ID")
    @GetMapping("/find/{id}")
    ResponseEntity<Employee> findEmployee(@PathVariable @Min(1) long id) throws EntityNotFoundException {
        Employee employee = employeeService.findById(id);
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
        employee.setDepartment(departmentService.findByName(form.getDepartment()));
        employee.setEmploymentDate(form.getEmploymentDate());
        employee.setEndOfAdaptation(form.getEndOfAdaptation());
        employee.setStatus(form.getStatus());
        employee.setPosition(form.getPosition());
        employeeService.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные сотрудника")
    @PutMapping("/update/{id}")
    ResponseEntity<Employee> updateEmployee(@RequestBody EmployeeForm form, @PathVariable int id) {
        Employee employee = employeeService.findById(id);
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());
        employee.setDepartment(departmentService.findByName(form.getDepartment()));
        employee.setEmploymentDate(form.getEmploymentDate());
        employee.setEndOfAdaptation(form.getEndOfAdaptation());
        employee.setStatus(form.getStatus());
        employee.setPosition(form.getPosition());
        employeeService.edit(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Удалить сотрудника")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.delete(id);
        return new ResponseEntity<>("Сотрудник удален", HttpStatus.OK);
    }

}


