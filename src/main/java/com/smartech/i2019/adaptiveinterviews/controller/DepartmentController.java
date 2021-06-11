package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.DepartmentDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    DepartmentDaoImpl departmentDao;

    @GetMapping(path = "/departments")
    public String allDepartments(Model model) {
        List<Department> departments = departmentDao.list();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("/departments/{id}/edit")
    public String editDepartmentForm(@PathVariable int id, Model model) {
        Department department = departmentDao.getById(id);
        if (department == null) {
            throw new EntityNotFoundException("Отдел не найден");
        }

        model.addAttribute("department", department);
        return "departmentform";
    }

    @GetMapping("/departments/add")
    public String departmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "departmentform";
    }

    @PostMapping("/departments/update")
    public String createDepartment(@ModelAttribute("department") @Valid Department department,
                                   BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "departmentform";
        }
        departmentDao.add(department);
        return "redirect:/departments";
    }

    @GetMapping("/departments/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") int id) {
        departmentDao.delete(id);
        return "redirect:/departments";
    }

    @PostMapping("departments/{id}/update")
    public String editDepartment(@PathVariable int id,
                                 @ModelAttribute("department") @Valid Department departmentToUpdate,
                                 BindingResult result, ModelMap model) {
        Department department = departmentDao.getById(id);
        if (department == null) {
            throw new EntityNotFoundException("Отдел не найден");
        }
        if (result.hasErrors()) {
            return "departmentform";
        }
        departmentDao.update(departmentToUpdate, id);
        return "redirect:/departments";
    }


}
