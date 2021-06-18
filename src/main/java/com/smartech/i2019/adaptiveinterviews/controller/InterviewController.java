package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.InterviewDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.UserDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.util.InterviewForm;
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
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/interviews")
public class InterviewController {
    @Autowired
    InterviewDaoImpl interviewDao;
    @Autowired
    EmployeeDaoImpl employeeDao;
    @Autowired
    UserDaoImpl userDao;

    @GetMapping()
    public void foo(Model model) {
        review(model);
    }

    @GetMapping("/all")
    public String allInterviews(Model model) {
        List<Interview> interviews = interviewDao.list();
        model.addAttribute("interviews", interviews);
        return "interviews";
    }

    @GetMapping("/listByDate")
    public String listByDate(Model model, HttpServletRequest request) {
        List<Interview> interviews;
        try {
            interviews = interviewDao.listByDate(Date.valueOf(request.getParameter("listByDate")));
        } catch (IllegalArgumentException ex) {
            return "interviews";
        }
        model.addAttribute("interviews", interviews);
        return "interviews";
    }

    @GetMapping("/coming")
    public String comingEvents(Model model) {
        List<Interview> interviews = interviewDao.listTodayAndAfter();
        model.addAttribute("interviews", interviews);
        return "interviews";
    }

    @GetMapping("/review")
    public String review(Model model) {
        List<Interview> interviews = interviewDao.listWithoutReview();
        model.addAttribute("interviews", interviews);
        return "interviews";
    }

    @GetMapping("/lastName")
    public String lastName(Model model, HttpServletRequest request) {
        List<Employee> employees = employeeDao.getByLastName(request.getParameter("lastName"));
        List<Interview> interviews = new ArrayList<>();
        for (Employee employee : employees) {
            interviews.addAll(new ArrayList<>(employee.getInterviews()));
        }
        model.addAttribute("interviews", interviews);
        return "interviews";
    }

    @GetMapping("/create")
    public ModelAndView createInterviewForm(Model model) {
        ModelAndView mav = new ModelAndView("interviewformcreate");
        mav.addObject("interviewFormCreate", new InterviewForm());
        mav.addObject("users", userDao.list());
        mav.addObject("names", getNames());
        return mav;
    }

    @PostMapping("/add")
    public String createInterview(@ModelAttribute("interviewFormCreate") @Valid InterviewForm form,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasFieldErrors("date")) {
                result.rejectValue("date1", "error.InterviewFormCreate",
                        "Заполните обязательное поле");
            }
            model.addAttribute("users", userDao.list());
            model.addAttribute("names", getNames());
            return "interviewformcreate";
        }
        Employee employee = employeeDao.getByName(form.getFirstName(), form.getLastName());
        if (employee == null) {
            result.rejectValue("employeeName", "error.InterviewFormCreate",
                    "Такого сотрудника нет в базе");
            model.addAttribute("users", userDao.list());

            return "interviewformcreate";
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

    private List<String> getNames() {
        List<String> names = new ArrayList<>();
        List<Employee> employees = employeeDao.listForNewInterview();
        for (Employee emp : employees) {
            names.add(emp.getLastName() + " " + emp.getFirstName());
        }
        return names;
    }

    @GetMapping("/delete/{id}")
    public String deleteInterwiew(@PathVariable(value = "id") int id) {
        interviewDao.delete(id);
        return "redirect:/interviews?all=yes";
    }

    @GetMapping("/edit/{id}")
    public String findInterview(@PathVariable @Min(1) int id, Model model) throws EntityNotFoundException {
        Interview interview = interviewDao.getById(id);
        if (interview == null) {
            throw new EntityNotFoundException("Беседа не найдена");
        }
        List<User> users = userDao.list();
        List<String> names = getNames();
        model.addAttribute("interviewFormEdit", new InterviewForm(interview));
        model.addAttribute("users", users);
        model.addAttribute("names", names);
        return "interviewformedit";
    }

    @PostMapping("/edit/update")
    public String editInterview(@ModelAttribute("interviewFormEdit") @RequestParam("id") int id,
                                @Valid InterviewForm form, BindingResult result, Model model) {
        Interview interview = interviewDao.getById(id);
        if (interview == null) {
            throw new EntityNotFoundException("Беседа не найдена");
        }
        Employee employee = employeeDao.getByName(form.getFirstName(), form.getLastName());
        if (employee == null) {
            result.rejectValue("employeeName", "error.InterviewFormEdit",
                    "Такого сотрудника нет в базе");
            model.addAttribute("users", userDao.list());
            model.addAttribute("names", getNames());
            return "interviewformedit";
        }
        interview.setDescription(form.getDescription());
        interview.setDate(form.getDate());
        interview.setUser(userDao.getByUsername(form.getNameOfUser()));
        interview.setEmployee(employee);
        interview.setName(form.getName());
        interviewDao.update(interview, id);
        return "redirect:/interviews";
    }


}
