package com.smartech.i2019.adaptiveinterviews.dao;

import com.smartech.i2019.adaptiveinterviews.api.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM", new Locale("ru"));

    public void sendRemindInterviewEmail(String to, String user, String employee, Date date) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Напоминание");
        msg.setText("Здравствуйте, " + user + ". \n " + simpleDateFormat.format(date) + " у Вас запланирована беседа с " + employee + ".");
        javaMailSender.send(msg);
    }

    public void sendRemindReviewEmail(String to, String user, String employee, Date date) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Напоминание");
        msg.setText("Здравствуйте, " + user + ". \n " + simpleDateFormat.format(date) + " Вы проводили беседу с " + employee + ". Оставьте отзыв");
        javaMailSender.send(msg);
    }
}