package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.DepartmentDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.util.EmployeeForm;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RequestMapping("/employees")
@Tag(name = "Employee", description = "The Employee API")
@Controller
public class EmployeeController {
    @Autowired
    private DepartmentDaoImpl departmentDao;
    @Autowired
    private EmployeeDaoImpl employeeDao;

    @GetMapping()
    public void foo(Model model) {
        allEmployees(model);
    }

    @GetMapping("/findByLastName")
    public String findByLastName(Model model, HttpServletRequest request) {
        List<Employee> employees = employeeDao.listByLastName(request.getParameter("lastName"));
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/adaptation")
    public String adaptation(Model model) {
        List<Employee> employees = employeeDao.listInAdaptation();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/all")
    public String allEmployees(Model model) {
        List<Employee> employees = employeeDao.list();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/{id}")
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

    @GetMapping("/create")
    public ModelAndView getEmployeeForm(Model model) {
        ModelAndView mav = new ModelAndView("employeeformcreate");
        mav.addObject("employeeFormCreate", new EmployeeForm());
        mav.addObject("departments", departmentDao.list());
        return mav;
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employeeFormCreate") @Valid EmployeeForm form,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("employmentDate")) {
                result.rejectValue("date1", "error.employeeFormCreate",
                        "Заполните обязательное поле");
            }
            if (result.hasFieldErrors("endOfAdaptation")) {
                result.rejectValue("date2", "error.employeeFormCreate",
                        "Заполните обязательное поле");
            }
            model.addAttribute("departments", departmentDao.list());
            return "employeeformcreate";
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

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") int id) {
        employeeDao.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeePage(@PathVariable(name = "id") int id, Model model) {
        Employee employee = employeeDao.getById(id);
        if (employee == null) {
            throw new EntityNotFoundException("Сотрудник не найден");
        }
        model.addAttribute("departments", departmentDao.list());
        model.addAttribute("employeeFormEdit", new EmployeeForm(employee));
        return "employeeformedit";
    }

    @PostMapping("/edit/update")
    public String updateEmployee(@RequestParam("id") int id, Model model,
                                 @ModelAttribute("interviewFormEdit") @Valid EmployeeForm form,
                                 BindingResult result) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("date")) {
                result.rejectValue("date1", "error.interviewFormEdit",
                        "Заполните обязательное поле");
            }
            model.addAttribute("departments", departmentDao.list());
            return "employeeformedit";
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


