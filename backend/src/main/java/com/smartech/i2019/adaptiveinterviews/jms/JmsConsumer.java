package com.smartech.i2019.adaptiveinterviews.jms;

import com.smartech.i2019.adaptiveinterviews.model.EmailSendEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsConsumer {

    @JmsListener(destination = "${queue.email}")
    public void receive(EmailSendEvent event) {
        log.info("Received message! Email has been send: ({})", event.toString());
    }

}
