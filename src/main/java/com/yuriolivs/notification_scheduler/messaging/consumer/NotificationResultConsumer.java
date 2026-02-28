package com.yuriolivs.notification_scheduler.messaging.consumer;

import com.yuriolivs.notification.shared.domain.notification.NotificationResult;
import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_scheduler.config.RabbitMqConfig;
import com.yuriolivs.notification_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.notification_scheduler.domain.schedule.enums.ScheduleStatus;
import com.yuriolivs.notification_scheduler.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationResultConsumer {
    private final SchedulerService service;

    @RabbitListener(queues = RabbitMqConfig.RESULT_QUEUE)
    public void consume(NotificationResult result) {
        try {
            ScheduledNotification notification = service.findScheduledNotification(result.getScheduleId());

            if (result.success()) {
                notification.setStatus(ScheduleStatus.EXECUTED);
            } else {
                notification.setStatus(ScheduleStatus.FAILED);
            }

            service.save(notification);
        } catch (HttpNotFoundException ex) {
            log.error("Notification was not found in database.", ex);
        }
    }
}
