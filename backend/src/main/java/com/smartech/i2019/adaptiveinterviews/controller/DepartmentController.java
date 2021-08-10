package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.DepartmentService;
import com.smartech.i2019.adaptiveinterviews.dto.DepartmentDTO;
import com.smartech.i2019.adaptiveinterviews.dto.mapper.DepartmentMapper;
import com.smartech.i2019.adaptiveinterviews.model.Department;
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

    // FIXME: в этом маппинге не нужно слово "delete"
    //        достаточно просто @DeleteMapping("/{id}")
    //
    @Operation(summary = "Удалить отдел")
    @DeleteMapping("/{id}")
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
    @GetMapping("/{id}")
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
    @PutMapping("/{id}")
    Department updateDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department department = DepartmentMapper.INSTANCE.departmentDTOToDepartment(departmentDTO);
        departmentService.edit(department);
        return department;
    }

}
