package com.smartech.i2019.adaptiveinterviews.api;

import com.smartech.i2019.adaptiveinterviews.model.Interview;

import java.sql.Date;
import java.util.List;

public interface InterviewDao {
    void add(Interview interview);

    void update(Interview interview, int id);

    void delete(int id);

    List<Interview> listByEmployee(int id);

    List<Interview> listByDateNextDay();

    List<Interview> listByDateSubtractDay();

    List<Interview> listByDate(Date date);

    List<Interview> list();

    List<Interview> listTodayAndAfter();

    List<Interview> listWithoutReview();

    Interview getById(int id);
}
