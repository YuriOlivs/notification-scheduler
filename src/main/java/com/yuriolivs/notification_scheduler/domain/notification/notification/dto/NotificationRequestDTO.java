package com.yuriolivs.notification_scheduler.domain.notification.notification.dto;

import com.yuriolivs.notification_scheduler.domain.notification.notification.enums.NotificationChannel;
import com.yuriolivs.notification_scheduler.domain.notification.notification.enums.NotificationPriority;
import com.yuriolivs.notification_scheduler.domain.notification.notification.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record NotificationRequestDTO(
    @NotBlank
    String idempotencyKey,

    NotificationChannel channel,

    @NotBlank
    String recipient,

    NotificationType type,

    String template,

    NotificationPriority priority,

    @NotNull
    Map<String, String> payload
) {
}
