package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.InterviewService;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.repository.InterviewRepository;
import com.smartech.i2019.adaptiveinterviews.util.Dates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {
    @Autowired
    InterviewRepository interviewRepository;

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
        return interviewRepository.getById(id);
    }

    @Override
    public List<Interview> listByEmployee(long id) {
        return interviewRepository.getEmployees(id);
    }

    @Override
    public List<Interview> listByDateNextDay() {
        Dates date = new Dates();
        return interviewRepository.findByDate(date.getSqlNextDay());
    }

    @Override
    public List<Interview> listByDateSubtractDay() {
        Dates date = new Dates();
        return interviewRepository.findByDate(date.getSqlSubtractDay());
    }

    @Override
    public List<Interview> listByDate(Date date) {
        return interviewRepository.findByDate(date);
    }

    @Override
    public List<Interview> listTodayAndAfter() {
        Dates date = new Dates();
        return interviewRepository.findAllByDateGreaterThanEqualAndDescriptionEquals(date.getSqlToday(), "");
    }

    @Override
    public List<Interview> listWithoutReview() {
        return interviewRepository.findAllByDescriptionEquals("");
    }

    @Override
    public Dates getDates() {
        return null;
    }
}
