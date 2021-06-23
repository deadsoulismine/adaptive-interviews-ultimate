package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.DepartmentDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Tag(name="Отделы", description="Взаимодействие с отделами")
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentDaoImpl departmentDao;

    @Operation(summary = "Список всех отделов")
    @GetMapping()
    ResponseEntity<List<Department>> findAll() {
        List<Department> departments = departmentDao.list();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @Operation(summary = "Добавить отдел")
    @PostMapping()
    ResponseEntity<Department> newDepartment(@Valid @RequestBody Department newDepartment) {
        departmentDao.add(newDepartment);
        return new ResponseEntity<>(newDepartment, HttpStatus.OK);
    }

    @Operation(summary = "Удалить отдел")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<String> deleteDepartment(@PathVariable int id) {
        departmentDao.delete(id);
        return new ResponseEntity<>("Department deleted", HttpStatus.OK);
    }

    @Operation(summary = "Найти отдел по ID")
    @GetMapping("/{id}")
    ResponseEntity<Department> findDepartment(@PathVariable @Min(1) int id) {
        Department department = departmentDao.getById(id);
        if (department == null) {
            throw new EntityNotFoundException("Отдел не найден");
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные отдела")
    @PutMapping("/{id}")
    ResponseEntity<Department> updateDepartment(@RequestBody Department newDepartment, @PathVariable int id) {
        departmentDao.update(newDepartment, id);
        return new ResponseEntity<>(newDepartment, HttpStatus.OK);
    }

}
