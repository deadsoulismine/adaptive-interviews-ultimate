package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import com.smartech.i2019.adaptiveinterviews.util.DepartmentForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    Department addDepartment(@RequestBody DepartmentForm departmentForm) {
        Department department = new Department();
        department.setName(departmentForm.getName());
        department.setSupervisor(departmentForm.getSupervisor());
        departmentService.add(department);
        return department;
    }

    // FIXME: в этом маппинге не нужно слово "delete"
    //        достаточно просто @DeleteMapping("/{id}")
    //
    @Operation(summary = "Удалить отдел")
    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    String deleteDepartment(@PathVariable long id) {
        departmentService.delete(id);
        return "Department deleted";
    }

    // FIXME: в этом маппинге не нужно слово "find"
    //        достаточно просто @GetMapping("/{id}")
    //
    //        также проверка на null толком не работает
    //        в ответе не приходит локализованная строка
    //        чуть позже прикрутим обработчик
    //
    @Operation(summary = "Найти отдел по ID")
    @GetMapping("/find/{id}")
    Department findDepartment(@PathVariable @Min(1) long id) {
//        Department department = departmentService.findById(id);
//        if (department == null) {
//            throw new EntityNotFoundException("Отдел не найден");
//        }
//        return department;

        return departmentService.findById(id);
    }

    // FIXME: в этом маппинге не нужно слово "update"
    //        достаточно просто @PutMapping("/{id}")
    //
    @Operation(summary = "Обновить данные отдела")
    @PutMapping("/update/{id}")
    Department updateDepartment(@PathVariable long id, @RequestBody DepartmentForm departmentForm) {
        Department department = departmentService.findById(id);
        department.setName(departmentForm.getName());
        department.setSupervisor(departmentForm.getSupervisor());
        departmentService.edit(department);
        return department;
    }

}
