package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.dao.InterviewDaoImpl;
import com.smartech.i2019.adaptiveinterviews.model.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SchedulerService {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    InterviewDaoImpl interviewDao;

    @Scheduled(cron = "0 0 9 * * *")  //каждый день в 9-00
    public void sendMailToRemindUsers() {
        List<Interview> interviewsNextDay = interviewDao.listByDateNextDay();
        List<Interview> interviewsSubtractDay = interviewDao.listByDateSubtractDay();
        if (!interviewsNextDay.isEmpty()) {
            try {
                for (Interview interview : interviewsNextDay) {
                    String to = interview.getUser().getEmail();
                    String user = interview.getUser().getName();
                    String employee = interview.getEmployee().getFirstName() + " " + interview.getEmployee().getLastName();
                    Date date = new Date(interview.getDate().getTime());
                    emailService.sendRemindInterviewEmail(to, user, employee, date);
                }
            } catch (Exception ex) {
            }
        }
        if (!interviewsSubtractDay.isEmpty()) {
            try {
                for (Interview interview : interviewsSubtractDay) {
                    String to = interview.getUser().getEmail();
                    String user = interview.getUser().getName();
                    String employee = interview.getEmployee().getFirstName() + " " + interview.getEmployee().getLastName();
                    Date date = new Date(interview.getDate().getTime());
                    if (interview.getDescription().isEmpty()) {
                        emailService.sendRemindReviewEmail(to, user, employee, date);
                    }
                }
            } catch (Exception ex) {
            }
        }
    }
}
