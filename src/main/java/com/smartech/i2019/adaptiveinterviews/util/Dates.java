package com.smartech.i2019.adaptiveinterviews.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;

@Component
@Scope("prototype")
public class Dates {
    private final java.util.Date dateSubtractDay = subtractDay();
    private final Date sqlSubtractDay = new Date(subtractDay().getTime());
    private final java.util.Date dateNextDay = nextDay();
    private final Date sqlNextDay = new Date(nextDay().getTime());
    private final Date sqlToday = new Date(new java.util.Date().getTime());

    public java.util.Date getDateNextDay() {
        return dateNextDay;
    }

    public Date getSqlNextDay() {
        return sqlNextDay;
    }

    public java.util.Date getDateSubtractDay() {
        return dateSubtractDay;
    }

    public Date getSqlToday() {
        return sqlToday;
    }

    public Date getSqlSubtractDay() {
        return sqlSubtractDay;
    }

    public java.util.Date subtractDay() {
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public java.util.Date nextDay() {
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, +1);
        return cal.getTime();
    }
}
