package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.InterviewService;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.repository.InterviewRepository;
import com.smartech.i2019.adaptiveinterviews.repository.specification.InterviewSpecification;
import com.smartech.i2019.adaptiveinterviews.util.Dates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewSpecification interviewSpecification;
    private final Dates date;

    @Override
    public void add(Interview interview) {
        interviewRepository.saveAndFlush(interview);
    }

    @Override
    public void edit(Interview interview) {
        interviewRepository.saveAndFlush(interview);
    }

    @Override
    public void delete(long id) {
        interviewRepository.deleteById(id);
    }

    @Override
    public List<Interview> findAll() {
        return interviewRepository.findAll();
    }

    @Override
    public Interview findById(long id) {
        return interviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Interview> listByDateNextDay() {
        return interviewRepository.findAll(interviewSpecification.hasDate(date.getSqlNextDay()));
    }

    @Override
    public List<Interview> listByDateSubtractDay() {
        return interviewRepository.findAll(interviewSpecification.hasDate(date.getSqlSubtractDay()));
    }

}
