package com.yuriolivs.notification_scheduler.domain.schedule.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "notifications_scheduled",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_scheduled_idempotency",
                        columnNames = "idempotencyKey"
                )
        }
)
public class ScheduledNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID notificationId;

    private Boolean isActive;

    private LocalDate date;

    private Time time;
}
