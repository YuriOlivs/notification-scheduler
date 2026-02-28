package com.yuriolivs.notification_scheduler.domain.schedule.dto;

import com.yuriolivs.notification_scheduler.domain.notification.dto.NotificationRequestDTO;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleRequestDTO(
        @NotBlank
        String idempotencyKey,

        @NotNull
        NotificationRequestDTO notification,

        @Future
        LocalDateTime dateTime
) {}
