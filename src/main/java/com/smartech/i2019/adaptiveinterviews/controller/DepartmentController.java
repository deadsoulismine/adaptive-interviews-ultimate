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
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentDaoImpl departmentDao;

    @GetMapping()
    public String allDepartments(Model model) {
        List<Department> departments = departmentDao.list();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@PathVariable int id, Model model) {
        Department department = departmentDao.getById(id);
        if (department == null) {
            throw new EntityNotFoundException("Отдел не найден");
        }

        model.addAttribute("departmentEdit", department);
        return "departmentformedit";
    }

    @GetMapping("/create")
    public String departmentForm(Model model) {
        model.addAttribute("departmentCreate", new Department());
        return "departmentformcreate";
    }

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute("departmentCreate") @Valid Department department,
                                BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "departmentformcreate";
        }
        departmentDao.add(department);
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") int id) {
        departmentDao.delete(id);
        return "redirect:/departments";
    }

    @PostMapping("/edit/update")
    public String editDepartment(@RequestParam("id") @ModelAttribute("departmentEdit") int id,
                                 @Valid Department departmentToUpdate, BindingResult result, ModelMap model) {
        Department department = departmentDao.getById(id);
        if (department == null) {
            throw new EntityNotFoundException("Отдел не найден");
        }
        if (result.hasErrors()) {
            return "departmentformedit";
        }
        departmentDao.update(departmentToUpdate, id);
        return "redirect:/departments";
    }


}
