package com.aglaya.service;

import com.aglaya.enums.EmailType;
import com.aglaya.enums.OutboxStatus;
import com.aglaya.model.EmailOutbox;
import com.aglaya.repository.EmailOutboxRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailOutboxService {
    EmailOutboxRepository emailOutboxRepository;

    EmailService emailService;

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

    /**
     * Обработка всех ожидающих писем из outbox
     */
    @Transactional
    public void processPendingEmails() {
        var pendingEmails = emailOutboxRepository.findByStatus(OutboxStatus.PENDING);

        for (var outbox : pendingEmails) {
            try {
                emailService.sendEmail(outbox.getRecipient(), outbox.getSubject(), outbox.getText());

                outbox.setStatus(OutboxStatus.SENT);
                outbox.setSentAt(LocalDateTime.now());
                emailOutboxRepository.save(outbox);

            } catch (Exception e) {
                outbox.setAttempts(outbox.getAttempts() + 1);
                emailOutboxRepository.save(outbox);
            }
        }
    }
}
