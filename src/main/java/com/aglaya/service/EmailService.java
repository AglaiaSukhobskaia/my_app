package com.aglaya.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    @NonFinal
    @Value("${spring.mail.username}")
    String email;

    JavaMailSender mailSender;

    /**
     * Отправка email-сообщения
     *
     * @param to      email-адрес получателя
     * @param subject тема письма
     * @param text    текст письма
     */
    public void sendEmail(String to, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(email);

        mailSender.send(message);
    }
}
