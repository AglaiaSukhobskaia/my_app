package com.aglaya.service;

import com.aglaya.enums.EmailType;
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
     * @param to        email-адрес получателя
     * @param emailType тип письма
     */
    public void sendEmail(String to, EmailType emailType) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(emailType.getSubject());
        message.setText(emailType.getText());
        message.setFrom(email);

        mailSender.send(message);
    }
}
