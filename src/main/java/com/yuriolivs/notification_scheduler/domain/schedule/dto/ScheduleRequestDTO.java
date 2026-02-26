package com.yuriolivs.notification_scheduler.domain.schedule.dto;

import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;

import java.time.LocalDateTime;

public record ScheduleRequestDTO(
        String idempotencyKey,
        NotificationRequestDTO notification,
        LocalDateTime dateTime
) {}
