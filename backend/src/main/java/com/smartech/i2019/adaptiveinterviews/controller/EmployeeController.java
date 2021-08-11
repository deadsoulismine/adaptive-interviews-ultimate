package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.dto.EmployeeDTO;
import com.smartech.i2019.adaptiveinterviews.dto.mapper.EmployeeMapper;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.util.exception.EmployeeNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
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
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Operation(summary = "Показать всех сотрудников")
    @GetMapping()
    List<Employee> allEmployees() {
        return employeeService.findAll();
    }

    @Operation(summary = "Получить список бесед по ID пользователя")
    @GetMapping("/interviews/{id}")
    List<Interview> getInterviews(@PathVariable @Min(1) long id) throws EntityNotFoundException {
        //null check
        return employeeService.getInterviews(id);
    }

    @Operation(summary = "Найти сотрудника по ID")
    @GetMapping("/{id}")
    Employee findEmployee(@PathVariable @Min(1) long id) throws EmployeeNotFoundException {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудника с указанным идентификатором нет в базе данных");
        }
        return employee;
    }

    @Operation(summary = "Добавить сотрудника")
    @PostMapping("/add")
    Employee addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employeeService.add(employee);
        return employee;
    }

    @Operation(summary = "Обновить данные сотрудника")
    @PutMapping("/{id}")
    Employee updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employeeService.edit(employee);
        return employee;
    }

    @Operation(summary = "Удалить сотрудника")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    String deleteEmployee(@PathVariable long id) {
        employeeService.delete(id);
        return "Сотрудник удален";
    }

}


