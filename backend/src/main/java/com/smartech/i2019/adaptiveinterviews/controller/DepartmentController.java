package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.dto.DepartmentDTO;
import com.smartech.i2019.adaptiveinterviews.dto.mapper.DepartmentMapper;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import com.smartech.i2019.adaptiveinterviews.util.exception.DepartmentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    List<Department> findAll() {
        return departmentService.findAll();
    }

    @Operation(summary = "Добавить отдел")
    @PostMapping("/add")
    Department addDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
        departmentService.add(department);
        return department;
    }

    @Operation(summary = "Удалить отдел")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    String deleteDepartment(@PathVariable long id) {
        departmentService.delete(id);
        return "Department deleted";
    }

    @Operation(summary = "Найти отдел по ID")
    @GetMapping("/{id}")
    Department findDepartment(@PathVariable @Min(1) long id) throws DepartmentNotFoundException {
        return departmentService.findById(id);
    }

    @Operation(summary = "Обновить данные отдела")
    @PutMapping("/{id}")
    Department updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
        departmentService.edit(department);
        return department;
    }

}
