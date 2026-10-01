package com.aglaya.scheduler;

import com.aglaya.service.EmailOutboxService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailOutboxScheduler {
    EmailOutboxService emailOutboxService;

    /**
     * Отправка всех писем со статусом PENDING каждые 10 секунд
     */
    @Scheduled(fixedDelay = 10000)
    public void processOutbox() {
        emailOutboxService.processPendingEmails();
    }
}
