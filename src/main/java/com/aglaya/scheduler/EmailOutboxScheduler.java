package com.aglaya.scheduler;

import com.aglaya.service.EmailOutboxService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
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
    @SchedulerLock(name = "processPendingEmails", lockAtLeastFor = "10s", lockAtMostFor = "30s")
    public void processPendingEmails() {
        emailOutboxService.processPendingEmails();
    }
}
