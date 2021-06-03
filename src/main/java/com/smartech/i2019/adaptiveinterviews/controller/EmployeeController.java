package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.DepartmentDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.util.EmployeeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class EmployeeController {
    @Autowired
    private DepartmentDaoImpl departmentDao;
    @Autowired
    private EmployeeDaoImpl employeeDao;

    @GetMapping(path = "/employees")
    public String allEmployees(Model model, HttpServletRequest request) {
        List<Employee> employees = employeeDao.listInAdaptation();
        if (request.getParameter("findLastName") != null) {
            employees = employeeDao.listByLastName(request.getParameter("findLastName"));
        }
        if (request.getParameter("adaptation") != null) {
            employees = employeeDao.listInAdaptation();
        }
        if (request.getParameter("all") != null) {
            employees = employeeDao.list();
        }
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/{id}")
    public String showEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("employee", employee);
        model.addAttribute("interviews", employee.getInterviews());
        model.addAttribute("files", employee.getFiles());
        return "employee";
    }

    @GetMapping("/employees/add")
    public ModelAndView getEmployeeForm(Model model) {
        ModelAndView mav = new ModelAndView("employeeform");
        mav.addObject("employeeForm", new EmployeeForm());
        mav.addObject("departments", departmentDao.list());
        return mav;
    }

    @PostMapping("/employees/update")
    public String addEmployee(@Valid EmployeeForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("employmentDate")) {
                result.rejectValue("date1", "error.employeeForm", "Заполните обязательное поле");
            }
            if (result.hasFieldErrors("endOfAdaptation")) {
                result.rejectValue("date2", "error.employeeForm", "Заполните обязательное поле");
            }
            model.addAttribute("departments", departmentDao.list());
            return "employeeform";
        }
        Employee employee = new Employee();
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());
        employee.setDepartment(departmentDao.getByName(form.getDepartment()));
        employee.setEmploymentDate(form.getEmploymentDate());
        employee.setEndOfAdaptation(form.getEndOfAdaptation());
        employee.setStatus(form.getStatus());
        employee.setPosition(form.getPosition());
        employeeDao.add(employee);
        model.addAttribute("employees", employeeDao.list());

        return "redirect:/employees/" + employee.getId();
    }

    @GetMapping("/employees/{id}/edit")
    public String showEditEmployeePage(@PathVariable(name = "id") int id, Model model) {

        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("departments", departmentDao.list());
        model.addAttribute("employeeForm", new EmployeeForm(employee));
        return "employeeform";
    }

    @PostMapping("/employees/{id}/update")
    public String updateEmployee(@PathVariable(name = "id") int id, Model model, @Valid EmployeeForm form,
                                 BindingResult result) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("date")) {
                result.rejectValue("date1", "error.interviewForm", "Заполните обязательное поле");
            }
            model.addAttribute("departments", departmentDao.list());
            model.addAttribute("employeeForm", form);
            return "employeeform";
        }
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());
        employee.setDepartment(departmentDao.getByName(form.getDepartment()));
        employee.setEmploymentDate(form.getEmploymentDate());
        employee.setEndOfAdaptation(form.getEndOfAdaptation());

        employee.setStatus(form.getStatus());
        employee.setPosition(form.getPosition());
        employeeDao.update(employee, id);
        return "redirect:/employees/" + id;
    }


}


