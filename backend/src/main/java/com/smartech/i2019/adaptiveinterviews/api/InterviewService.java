package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Interview;

import java.util.List;

public interface InterviewService {

    void add(Interview interview);

    void edit(Interview interview);

    void delete(long id);

    List<Interview> findAll();

    Interview findById(long id);

    List<Interview> listByDateNextDay();

    List<Interview> listByDateSubtractDay();

}
