package com.smartech.i2019.adaptiveinterviews.service;

import java.util.Date;

public interface EmailService {
    void sendRemindInterviewEmail(String to, String user, String employee, Date date);

    void sendRemindReviewEmail(String to, String user, String employee, Date date);
}
