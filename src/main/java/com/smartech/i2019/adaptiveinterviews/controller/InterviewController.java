package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.dao.EmployeeDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.InterviewDaoImpl;
import com.smartech.i2019.adaptiveinterviews.dao.UserDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.util.InterviewForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/interviews")
@Tag(name="Беседы", description="Взаимодействие с беседами")
public class InterviewController {
    @Autowired
    InterviewDaoImpl interviewDao;
    @Autowired
    EmployeeDaoImpl employeeDao;
    @Autowired
    UserDaoImpl userDao;

    @Operation(summary = "Список всех бесед")
    @GetMapping()
    ResponseEntity<List<Interview>> list() {
        List<Interview> interviews = interviewDao.list();
        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }

    @Operation(summary = "Найти беседу по ID")
    @GetMapping("/{id}")
    ResponseEntity<Interview> findInterview(@PathVariable @Min(1) int id) throws EntityNotFoundException {
        Interview interview = interviewDao.getById(id);
        if (interview == null) {
            throw new EntityNotFoundException("Беседа не найдена");
        }
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

    @Operation(summary = "Добавить беседу")
    @PostMapping("/add")
    ResponseEntity<Employee> addInterview(@RequestBody InterviewForm form) {
        Employee employee = employeeDao.getByName(form.getFirstName(), form.getLastName());
        Interview interview = new Interview();
        interview.setDescription(form.getDescription());
        interview.setDate(form.getDate());
        interview.setUser(userDao.getByUsername(form.getNameOfUser()));
        interview.setEmployee(employee);
        interview.setName(form.getName());
        interviewDao.add(interview);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Удалить беседу")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteInterview(@PathVariable int id) {
        interviewDao.delete(id);
        return new ResponseEntity<>("Беседа удалена", HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные беседы")
    @PutMapping("/{id}")
    ResponseEntity<Interview> updateInterview(@RequestBody Interview newInterview, @PathVariable int id) {
        interviewDao.update(newInterview, id);
        return new ResponseEntity<>(newInterview, HttpStatus.OK);
    }

}
