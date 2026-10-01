package com.aglaya.repository;

import com.aglaya.enums.OutboxStatus;
import com.aglaya.model.EmailOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, Long> {
    List<EmailOutbox> findByStatus(OutboxStatus status);
}
