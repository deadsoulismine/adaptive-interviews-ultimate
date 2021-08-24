package com.smartech.i2019.adaptiveinterviews.jms;

import com.smartech.i2019.adaptiveinterviews.model.EmailSendEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsProducer {
    private final JmsTemplate jmsTemplate;

    public void send(String destination, EmailSendEvent event) {
        log.info("Email sending with data ({}) sending to destination ({})", event.toString(), destination);
        jmsTemplate.convertAndSend(destination, event);
    }

}
