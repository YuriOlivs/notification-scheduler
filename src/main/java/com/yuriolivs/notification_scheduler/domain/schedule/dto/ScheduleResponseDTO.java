package com.yuriolivs.notification_scheduler.domain.schedule.dto;

import com.yuriolivs.notification.shared.domain.notification.dto.NotificationResponseDTO;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationChannel;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationStatus;
import com.yuriolivs.notification_scheduler.domain.notification.Notification;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification_scheduler.domain.schedule.enums.ScheduleStatus;

import java.time.LocalDate;
import java.util.UUID;

public record ScheduleResponseDTO(
        UUID id,
        NotificationChannel channel,
        String recipient,
        ScheduleStatus status,
        LocalDate scheduledTo
) {
    public static ScheduleResponseDTO from(
            ScheduledNotification scheduled
    ) {
        return new ScheduleResponseDTO(
                scheduled.getId(),
                scheduled.getChannel(),
                scheduled.getRecipient(),
                scheduled.getStatus(),
                scheduled.getDate()
        );
    }
}
