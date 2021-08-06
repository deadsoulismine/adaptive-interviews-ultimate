package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.EmployeeService;
import com.smartech.i2019.adaptiveinterviews.api.InterviewService;
import com.smartech.i2019.adaptiveinterviews.api.UserService;
import com.smartech.i2019.adaptiveinterviews.model.Employee;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.util.InterviewForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/interviews")
@Tag(name = "Беседы", description = "Взаимодействие с беседами")
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;
    private final EmployeeService employeeService;
    private final UserService userService;

    @Operation(summary = "Список всех бесед")
    @GetMapping()
    ResponseEntity<List<Interview>> list() {
        List<Interview> interviews = interviewService.findAll();

        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }

    @Operation(summary = "Найти беседу по ID")
    @GetMapping("/find/{id}")
    ResponseEntity<Interview> findInterview(@PathVariable @Min(1) Long id) throws EntityNotFoundException {
        Interview interview = interviewService.findById(id);
        if (interview == null) {
            throw new EntityNotFoundException("Беседа не найдена");
        }
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

    @Operation(summary = "Добавить беседу")
    @PostMapping("/add")
    ResponseEntity<Employee> addInterview(@RequestBody InterviewForm form) {
        Interview interview = new Interview();
        interview.setName(form.getName());
        interview.setDescription("");
        interview.setDate(form.getDate());
        List<User> users = new ArrayList<>();
        for (int userIndex : form.getUsers()) {
            users.add(userService.findById(userIndex));
        }
        interview.setUsers(users);
        Employee employee = employeeService.findById(form.getEmployeeId());
        interview.setEmployee(employee);
        interviewService.add(interview);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Удалить беседу")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteInterview(@PathVariable Long id) {
        interviewService.delete(id);
        return new ResponseEntity<>("Беседа удалена", HttpStatus.OK);
    }

    @Operation(summary = "Обновить данные беседы")
    @PutMapping("/update/{id}")
    ResponseEntity<Interview> updateInterview(@PathVariable Long id, @RequestBody InterviewForm form) {
        Interview interview = interviewService.findById(id);
        interview.setDate(form.getDate());
        interview.setDescription(form.getDescription());
        interview.setName(form.getName());
        List<User> users = new ArrayList<>();
        for (int userIndex : form.getUsers()) {
            users.add(userService.findById(userIndex));
        }
        interview.setUsers(users);
        Employee employee = employeeService.findById(form.getEmployeeId());
        interview.setEmployee(employee);
        interviewService.edit(interview);
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

}
