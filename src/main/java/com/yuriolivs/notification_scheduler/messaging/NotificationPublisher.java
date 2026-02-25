package com.yuriolivs.notification_scheduler.messaging;

import com.yuriolivs.notification.shared.domain.notification.NotificationSend;
import com.yuriolivs.notification.shared.domain.notification.enums.NotificationPriority;
import com.yuriolivs.notification_scheduler.domain.notification.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class NotificationPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final String EXCHANGE = "notification.exchange";

    public void publish(Notification notification, Map<String, String> payload) {
        String routingKey = notification.getChannel().name().toLowerCase();
        notification.setPriority(NotificationPriority.SCHEDULED);

        NotificationSend send = new NotificationSend(
                notification.getId(),
                notification.getPriority(),
                payload
        );

        rabbitTemplate.convertAndSend(
            EXCHANGE,
            routingKey,
            send,
            msg -> {
            msg.getMessageProperties()
                    .setPriority(notification.getPriority().value());

            return msg;
            }
        );
    }
}
