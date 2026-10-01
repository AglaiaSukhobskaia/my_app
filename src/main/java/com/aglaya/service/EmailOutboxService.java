package com.aglaya.service;

import com.aglaya.enums.EmailType;
import com.aglaya.model.EmailOutbox;
import com.aglaya.repository.EmailOutboxRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailOutboxService {
    EmailOutboxRepository emailOutboxRepository;

    /**
     * Создание запись в outbox для отложенной отправки письма
     *
     * @param recipient email получателя
     * @param emailType тип письма (определяет тему и текст)
     */
    @Transactional
    public void createEmailOutbox(String recipient, EmailType emailType) {
        var emailOutbox = EmailOutbox.builder()
                .recipient(recipient)
                .subject(emailType.getSubject())
                .text(emailType.getText())
                .build();

        emailOutboxRepository.save(emailOutbox);
    }
}
