package com.yuriolivs.notification_scheduler.domain.schedule.dto;

import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;

import java.sql.Time;
import java.time.LocalDate;

public record ScheduleRequestDTO(
        String idempotencyKey,
        NotificationRequestDTO notification,
        LocalDate date,
        Time time
) {}
