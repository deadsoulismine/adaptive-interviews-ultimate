package com.smartech.i2019.adaptiveinterviews.scheduler;

import com.smartech.i2019.adaptiveinterviews.model.Interview;
import com.smartech.i2019.adaptiveinterviews.model.User;
import com.smartech.i2019.adaptiveinterviews.service.EmailService;
import com.smartech.i2019.adaptiveinterviews.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSendScheduler {
    private final EmailService emailService;
    private final InterviewService interviewService;

    @Scheduled(cron = "* * 9 * * *") //каждый день в 9-00
    @Transactional
    public void sendMailToRemindUsers() {
        List<Interview> interviewsNextDay = interviewService.listByDateNextDay();
        List<Interview> interviewsSubtractDay = interviewService.listByDateSubtractDay();
        if (!interviewsNextDay.isEmpty()) {
            try {
                for (Interview interview : interviewsNextDay) {
                    String employee = interview.getEmployee().getFirstName() + " " + interview.getEmployee().getLastName();
                    Date date = new Date(interview.getDate().getTime());
                    for (User user : interview.getUsers()) {
                        String email = user.getEmail();
                        String name = user.getName();
                        emailService.sendRemindInterviewEmail(email, name, employee, date);
                        log.info("Пользователю ({}) отправлено напоминание о собеседовании с ({}) на почту ({}). " +
                                "Оно будет проведено ({})", name, employee, email, date);
                    }
                }
            } catch (Exception ex) {
                log.error("Не удалось отправить напоминание! {}", ex);
            }
        }
        if (!interviewsSubtractDay.isEmpty()) {
            try {
                for (Interview interview : interviewsSubtractDay) {
                    String employee = interview.getEmployee().getFirstName() + " " + interview.getEmployee().getLastName();
                    Date date = new Date(interview.getDate().getTime());
                    for (User user : interview.getUsers()) {
                        String email = user.getEmail();
                        String name = user.getName();

                        emailService.sendRemindInterviewEmail(email, name, employee, date);
                        log.info("Пользователю ({}) отправлено напоминание о собеседовании с ({}) на почту ({}). " +
                                "Оно будет проведено ({})", name, employee, email, date);
                        if (interview.getDescription().isEmpty()) {
                            emailService.sendRemindReviewEmail(email, name, employee, date);
                            log.info("Пользователю ({}) отправлено напоминание об отзыве в собеседовании" +
                                    " с ({}) на почту ({}). Оно будет проведено ({})", name, employee, email, date);
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("Не удалось отправить напоминание! {}", ex);
            }
        }
    }
}
