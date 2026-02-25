package com.yuriolivs.notification_scheduler.domain.schedule.entities;

import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification_scheduler.domain.schedule.enums.ScheduleStatus;
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

    private String idempotencyKey;

    @Column(nullable = false)
    private UUID notificationId;

    private String recipient;

    private NotificationChannel channel;

    private Boolean isActive;

    private ScheduleStatus status;

    private LocalDate date;

    private Time time;

    public ScheduledNotification(
            String idempotencyKey,
            UUID notificationId,
            String recipient,
            NotificationChannel channel,
            Boolean isActive,
            ScheduleStatus status,
            LocalDate date,
            Time time
    ) {
        this.idempotencyKey = idempotencyKey;
        this.notificationId = notificationId;
        this.recipient = recipient;
        this.channel = channel;
        this.isActive = isActive;
        this.status = status;
        this.date = date;
        this.time = time;
    }
}
