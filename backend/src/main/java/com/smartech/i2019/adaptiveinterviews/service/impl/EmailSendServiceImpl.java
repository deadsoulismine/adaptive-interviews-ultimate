package com.smartech.i2019.adaptiveinterviews.service.impl;

import com.smartech.i2019.adaptiveinterviews.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailSendServiceImpl implements EmailService {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru"));
    private final JavaMailSender javaMailSender;

    public void sendRemindInterviewEmail(String to, String user, String employee, Date date) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Напоминание");
        msg.setText("Здравствуйте, " + user + ". \n"
                + simpleDateFormat.format(date) + " у Вас запланирована беседа с " + employee + ".");
        try {
            javaMailSender.send(msg);
        } catch (MailException mailException) {
            mailException.printStackTrace();
        }
    }

    public void sendRemindReviewEmail(String to, String user, String employee, Date date) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Напоминание");
        msg.setText("Здравствуйте, " + user + ". \n"
                + simpleDateFormat.format(date) + " Вы проводили беседу с " + employee + ". Оставьте отзыв");
        try {
            javaMailSender.send(msg);
        } catch (MailException mailException) {
            mailException.printStackTrace();
        }
    }
}