package com.smartech.i2019.adaptiveinterviews.controller;

import com.smartech.i2019.adaptiveinterviews.api.InterviewService;
import com.smartech.i2019.adaptiveinterviews.dto.InterviewDTO;
import com.smartech.i2019.adaptiveinterviews.dto.mapper.InterviewMapper;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.util.exception.InterviewNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/interviews")
@Tag(name = "Беседы", description = "Взаимодействие с беседами")
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;
    private final InterviewMapper interviewMapper;

    @Operation(summary = "Список всех бесед")
    @GetMapping()
    List<Interview> list() {
        return interviewService.findAll();
    }

    @Operation(summary = "Найти беседу по ID")
    @GetMapping("/{id}")
    Interview findInterview(@PathVariable @Min(1) Long id) throws InterviewNotFoundException {
        Interview interview = interviewService.findById(id);
        if (interview == null) {
            throw new InterviewNotFoundException("Беседы с указанным идентификатором нет в базе данных");
        }
        return interview;
    }

    @Operation(summary = "Добавить беседу")
    @PostMapping("/add")
    Interview addInterview(@RequestBody InterviewDTO interviewDTO) {
        Interview interview = interviewMapper.interviewDTOtoInterview(interviewDTO);
        interviewService.add(interview);
        return interview;
    }

    @Operation(summary = "Удалить беседу")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    String deleteInterview(@PathVariable Long id) {
        interviewService.delete(id);
        return "Беседа удалена";
    }

    @Operation(summary = "Обновить данные беседы")
    @PutMapping("/{id}")
    Interview updateInterview(@RequestBody InterviewDTO interviewDTO) {
        Interview interview = interviewMapper.interviewDTOtoInterview(interviewDTO);
        interviewService.edit(interview);
        return interview;
    }
}
