package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import com.smartech.i2019.adaptiveinterviews.util.DepartmentForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@Tag(name = "Отделы", description = "Взаимодействие с отделами")
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(summary = "Список всех отделов")
    @GetMapping()
    ResponseEntity<List<Department>> findAll() {
        List<Department> departments = departmentService.findAll();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @Operation(summary = "Добавить отдел")
    @PostMapping("/add")
    ResponseEntity<Department> addDepartment(@RequestBody DepartmentForm departmentForm) {
        Department department = new Department();
        System.out.println(departmentForm.getSupervisor());
        department.setName(departmentForm.getName());
        department.setSupervisor(departmentForm.getSupervisor());
        departmentService.add(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @Operation(summary = "Удалить отдел")
    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<String> deleteDepartment(@PathVariable long id) {
        departmentService.delete(id);
        return new ResponseEntity<>("Department deleted", HttpStatus.OK);
    }

    @Operation(summary = "Найти отдел по ID")
    @GetMapping("/find/{id}")
    ResponseEntity<Department> findDepartment(@PathVariable @Min(1) long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            throw new EntityNotFoundException("Отдел не найден");
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные отдела")
    @PutMapping("/update/{id}")
    ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody DepartmentForm departmentForm) {
        Department department = departmentService.findById(id);
        department.setName(departmentForm.getName());
        department.setSupervisor(departmentForm.getSupervisor());
        departmentService.edit(department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

}
