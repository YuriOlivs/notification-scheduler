package com.yuriolivs.notification_scheduler.domain.schedule.dto;

import com.yuriolivs.notification_scheduler.domain.notification.notification.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleResponseDTO(
        String idempotencyKey,
        UUID id,
        String recipient,
        String channel,
        String type,
        NotificationStatus status,
        LocalDateTime scheduledTo
) {
}
