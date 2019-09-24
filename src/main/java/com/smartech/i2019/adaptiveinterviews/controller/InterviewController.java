package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.InterviewDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.UserDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.util.InterviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InterviewController {
    @Autowired
    InterviewDaoImpl interviewDao;
    @Autowired
    EmployeeDaoImpl employeeDao;
    @Autowired
    UserDaoImpl userDao;

    @GetMapping("/interviews/{id}/edit")
    public String findInterview(@PathVariable @Min(1) int id, Model model) throws EntityNotFoundException {
        Interview interview = interviewDao.getById(id);
        if (interview == null) {
            throw new EntityNotFoundException("Беседа не найдена");
        }
        List<User> users = userDao.list();
        users.remove(interview.getUser());
        List<String> names = getNames();
        names.remove(interview.getEmployee().getLastName() + " " + interview.getEmployee().getFirstName());
        model.addAttribute("interviewForm", new InterviewForm(interview));
        model.addAttribute("users", users);
        model.addAttribute("names", names);
        return "interviewform";
    }

    @RequestMapping(path = "/interviews", method = RequestMethod.GET)
    public String allInterviews(Model model, HttpServletRequest request) {
        List<Interview> interviews = new ArrayList<>();
        try {
            interviews = interviewDao.listByDate(Date.valueOf(request.getParameter("findDate")));
        } catch (IllegalArgumentException ex) {
            interviews = interviewDao.listTodayAndAfter();
        }
        if (request.getParameter("review") != null) {
            interviews = interviewDao.listWithoutReview();
        }
        if (request.getParameter("all") != null) {
            interviews = interviewDao.list();
        }
        if (request.getParameter("lastName") != null) {
            List<Employee> employees = employeeDao.getByLastName(request.getParameter("lastName"));
            interviews = new ArrayList<>();
                for (Employee employee : employees) {
                    interviews.addAll(new ArrayList<>(employee.getInterviews()));
                }

        }
        model.addAttribute("interviews", interviews);
        return "interviews";
    }

    @GetMapping("/interviews/add")
    public ModelAndView getInterviewForm(Model model) {
        ModelAndView mav = new ModelAndView("interviewform");
        mav.addObject("interviewForm", new InterviewForm());
        mav.addObject("users", userDao.list());
        mav.addObject("names", getNames());
        return mav;
    }

    private List<String> getNames() {
        List<String> names = new ArrayList<>();
        List<Employee> employees = employeeDao.listForNewInterview();
        for (Employee emp : employees) {
            names.add(emp.getLastName() + " " + emp.getFirstName());
        }
        return names;
    }

    @PostMapping("interviews/{id}/update")
    public String editInterview(@PathVariable int id, @Valid InterviewForm form, BindingResult result,
                                Model model) {
        Interview interview = interviewDao.getById(id);
        if (interview == null) {
            throw new EntityNotFoundException("Беседа не найдена");
        }
        Employee employee = employeeDao.getByName(form.getFirstName(), form.getLastName());
        if (employee == null) {
            result.rejectValue("employeeName", "error.interviewForm", "Такого сотрудника нет в базе");
            model.addAttribute("users", userDao.list());
            model.addAttribute("names", getNames());
            return "interviewform";
        }
        interview.setDescription(form.getDescription());
        interview.setDate(form.getDate());
        interview.setUser(userDao.getByUsername(form.getNameOfUser()));
        interview.setEmployee(employee);
        interview.setName(form.getName());
        interviewDao.update(interview, id);
        return "redirect:/interviews";
    }

    @PostMapping("/interviews/update")
    public String addInterview(@Valid InterviewForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("date")) {
                result.rejectValue("date1", "error.interviewForm", "Заполните обязательное поле");
            }
            model.addAttribute("users", userDao.list());
            model.addAttribute("names", getNames());
            return "interviewform";
        }
        Employee employee = employeeDao.getByName(form.getFirstName(), form.getLastName());
        if (employee == null) {
            result.rejectValue("employeeName", "error.interviewForm", "Такого сотрудника нет в базе");
            model.addAttribute("users", userDao.list());

            return "interviewform";
        }
        Interview interview = new Interview();
        interview.setDescription(form.getDescription());
        interview.setDate(form.getDate());
        interview.setUser(userDao.getByUsername(form.getNameOfUser()));
        interview.setEmployee(employee);
        interview.setName(form.getName());
        interviewDao.add(interview);
        return "redirect:/interviews";
    }
}
