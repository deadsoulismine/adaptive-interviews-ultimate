package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.InterviewService;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.repository.InterviewRepository;
import com.smartech.i2019.adaptiveinterviews.repository.specification.InterviewSpecification;
import com.smartech.i2019.adaptiveinterviews.util.Dates;
import com.smartech.i2019.adaptiveinterviews.util.exception.InterviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewSpecification interviewSpecification;
    private final Dates date;
    private static final Logger logger = LoggerFactory.getLogger(InterviewServiceImpl.class);

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
        Interview interview = interviewRepository.findById(id).orElse(null);
        if (interview == null) {
            logger.warn("Беседы с указанным идентификатором нет в базе данных: ({})", id);
            throw new InterviewNotFoundException("Беседы с указанным идентификатором нет в базе данных");
        }
        return interview;
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
