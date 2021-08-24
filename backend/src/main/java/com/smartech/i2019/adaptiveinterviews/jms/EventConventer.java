package com.smartech.i2019.adaptiveinterviews.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartech.i2019.adaptiveinterviews.model.EmailSendEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConventer implements MessageConverter {
    private final ObjectMapper mapper;

    @Override
    @NonNull
    public Message toMessage(@NonNull Object object, @NonNull Session session) throws JMSException, MessageConversionException {

        EmailSendEvent event = (EmailSendEvent) object;
        String payload = null;
        try {
            payload = mapper.writeValueAsString(event);
            log.info("Исходящее сообщение: {}", payload);
        } catch (IOException e) {
            log.error("Ошибка преобразования объекта");
        }
        TextMessage message = session.createTextMessage();
        message.setText(payload);
        return message;
    }

    @Override
    @NonNull
    public Object fromMessage(@NonNull Message message) throws JMSException, MessageConversionException {

        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        log.info("Входящее сообщение: {}", payload);
        EmailSendEvent event = null;
        try {
            event = mapper.readValue(payload, EmailSendEvent.class);
        } catch (IOException e) {
            log.error("Ошибка преобразования объекта");
        }
        assert event != null;
        return event;
    }
}
