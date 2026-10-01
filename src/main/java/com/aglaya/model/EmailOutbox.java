package com.aglaya.model;

import com.aglaya.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "email_outbox")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "recipient", nullable = false, length = 200)
    String recipient;

    @Column(name = "subject", nullable = false, length = 200)
    String subject;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    String text;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    OutboxStatus status = OutboxStatus.PENDING;

    @Builder.Default
    @Column(name = "attempts", nullable = false)
    int attempts = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "sent_at")
    LocalDateTime sentAt;
}
